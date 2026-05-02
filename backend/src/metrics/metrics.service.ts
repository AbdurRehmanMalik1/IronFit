import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository, Between } from 'typeorm';
import { HealthMetric } from 'src/entities/healthmetric.entity';
import { CreateMetricDto } from './dto/create-metric.dto';
import { UpdateMetricDto } from './dto/update-metrics.dto';
import { User } from 'src/entities/user.entity';
import { MetricDefinition } from 'src/entities/metric-definition.entity';
import { UserMetricSubscription } from 'src/entities/user-metric-subscription.entity';

@Injectable()
export class MetricsService {
  constructor(
    @InjectRepository(HealthMetric)
    private readonly metricRepo: Repository<HealthMetric>,

    @InjectRepository(User)
    private readonly userRepo: Repository<User>,

    @InjectRepository(MetricDefinition)
    private readonly definitionRepo: Repository<MetricDefinition>,

    @InjectRepository(UserMetricSubscription)
    private readonly subRepo: Repository<UserMetricSubscription>,


  ) { }

  // ➕ ADD METRIC
  async create(dto: CreateMetricDto, userId: string) {
    const user = await this.userRepo.findOneBy({ id: userId });

    if (!user) throw new NotFoundException('User not found');

    const metric = this.metricRepo.create({
      user,
      type: dto.type,
      value: dto.value,
      recordedAt: dto.recordedAt ?? new Date(),
    });

    return this.metricRepo.save(metric);
  }

  // 📥 GET USER METRICS (filtered)
  async findByUser(userId: string, type?: string, date?: string) {
    const where: any = {
      user: { id: userId },
    };

    if (type) where.type = type;

    if (date) {
      const start = new Date(date);
      const end = new Date(date);
      end.setHours(23, 59, 59, 999);

      where.recordedAt = Between(start, end);
    }

    return this.metricRepo.find({
      where,
      order: { recordedAt: 'DESC' },
    });
  }

  async update(id: string, dto: UpdateMetricDto, userId: string) {
    const metric = await this.metricRepo.findOne({
      where: {
        id,
        user: { id: userId },
      },
      relations: ['user'],
    });
    if (!metric) {
      throw new NotFoundException('Metric not found');
    }
    Object.assign(metric, dto);
    return this.metricRepo.save(metric);
  }

  async getSnapshot(userId: string) {
  // 1. subscriptions (allowed metrics)
  const subs = await this.subRepo.find({
    where: {
      user: { id: userId },
      isActive: true,
    },
  });

  const allowedTypes = new Set(subs.map(s => s.metricType));

  // 2. fetch latest metrics ONLY for user
  const metrics = await this.metricRepo.find({
    where: { user: { id: userId } },
    order: { recordedAt: 'DESC' },
  });

  // 3. keep latest per type
  const latestMap = new Map<string, HealthMetric>();

  for (const m of metrics) {
    if (!allowedTypes.has(m.type)) continue;
    if (!latestMap.has(m.type)) {
      latestMap.set(m.type, m);
    }
  }

  // 4. definitions map
  const definitions = await this.definitionRepo.find();
  const defMap = new Map(definitions.map(d => [d.key, d]));

  // 5. IMPORTANT: ensure ALL subscribed metrics exist
  const result = Array.from(allowedTypes).map(type => {
    const metric = latestMap.get(type);

    const def = defMap.get(type);

    return {
      type,
      value: metric?.value ?? 0,   // 👈 default value if missing
      name: def?.name ?? type,
      unit: def?.unit ?? '',
      icon: def?.icon ?? '',
      color: def?.color ?? '#000000',
    };
  });

  return result;
}

  async getDefinitions() {
    const defs = await this.definitionRepo.find({
      order: {
        name: 'ASC',
      },
    });

    return defs.map(def => ({
      type: def.key,
      name: def.name,
      unit: def.unit,
      icon: def.icon,
      color: def.color,
    }));
  }

  async getUserSubscriptions(userId: string) {
    return this.subRepo.find({
      where: { user: { id: userId }, isActive: true }
    });
  }
  async saveSubscriptions(userId: string, metrics: string[]) {
    // delete old
    await this.subRepo.delete({ user: { id: userId } });

    // insert new
    const entities = metrics.map(type =>
      this.subRepo.create({
        user: { id: userId },
        metricType: type,
        isActive: true
      })
    );

    return this.subRepo.save(entities);
  }

  async toggleSubscription(userId: string, metricType: string) {
    const existing = await this.subRepo.findOne({
      where: { user: { id: userId }, metricType }
    });

    if (existing) {
      existing.isActive = !existing.isActive;
      return this.subRepo.save(existing);
    }

    return this.subRepo.save(
      this.subRepo.create({
        user: { id: userId },
        metricType,
        isActive: true
      })
    );
  }
  async deactivate(userId: string, metricType: string) {
    return this.subRepo.update(
      { user: { id: userId }, metricType },
      { isActive: false }
    );
  }
}