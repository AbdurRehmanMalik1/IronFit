import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { MetricsService } from './metrics.service';
import { MetricsController } from './metrics.controller';
import { HealthMetric } from 'src/entities/healthmetric.entity';
import { User } from 'src/entities/user.entity';
import { MetricDefinition } from 'src/entities/metric-definition.entity';
import { UserMetricSubscription } from 'src/entities/user-metric-subscription.entity';

@Module({
  imports: [TypeOrmModule.forFeature([HealthMetric, User, MetricDefinition, UserMetricSubscription])],
  providers: [MetricsService],
  controllers: [MetricsController],
})
export class MetricsModule {}