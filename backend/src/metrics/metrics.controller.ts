import {
  Controller,
  Post,
  Get,
  Patch,
  Param,
  Body,
  Query,
  Req,
  UseGuards,
  Delete,
} from '@nestjs/common';

import { MetricsService } from './metrics.service';
import { CreateMetricDto } from './dto/create-metric.dto';
import { UpdateMetricDto } from './dto/update-metrics.dto';
import { UserId } from 'src/decorators/UserIdDecorator';
import { SessionGuard } from 'src/auth/auth.guard';

@UseGuards(SessionGuard)
@Controller('metrics')
export class MetricsController {
  constructor(private readonly metricsService: MetricsService) { }

  // -------------------------
  // 📥 DEFINITIONS (STATIC DATA)
  // -------------------------
  @Get('definitions')
  getDefinitions() {
    console.log('reached me')

    return this.metricsService.getDefinitions();
  }

  // -------------------------
  // 📊 SNAPSHOT
  // -------------------------
  @Get('snapshot')
  snapshot(@UserId() userId: string) {
    return this.metricsService.getSnapshot(userId);
  }

  // -------------------------
  // 📥 SUBSCRIPTIONS
  // -------------------------
  @Get('subscriptions')
  getSubscriptions(@Req() req) {
    return this.metricsService.getUserSubscriptions(req.session.userId);
  }

  @Post('subscriptions')
  saveSubscriptions(
    @Req() req,
    @Body() dto: { metrics: string[] },
  ) {
    return this.metricsService.saveSubscriptions(
      req.session.userId,
      dto.metrics,
    );
  }

  @Post('subscriptions/toggle')
  toggleMetric(
    @Req() req,
    @Body('metricType') metricType: string,
  ) {
    return this.metricsService.toggleSubscription(
      req.session.userId,
      metricType,
    );
  }

  @Delete('subscriptions/:type')
  remove(@Req() req, @Param('type') type: string) {
    return this.metricsService.deactivate(req.session.userId, type);
  }

  // -------------------------
  // 📊 METRICS (CRUD)
  // -------------------------
  @Post()
  create(@UserId() userId: string, @Body() dto: CreateMetricDto) {
    return this.metricsService.create(dto, userId);
  }

  // @Get('records')
  // find(
  //   @UserId() userId: string,
  //   @Query('type') type?: string,
  //   @Query('date') date?: string,
  // ) {
  //   return this.metricsService.findByUser(userId, type, date);
  // }

  // // ✏️ UPDATE a metric record
  // @Patch('records/:id')
  // update(
  //   @UserId() userId: string,
  //   @Param('id') id: string,
  //   @Body() dto: UpdateMetricDto,
  // ) {
  //   return this.metricsService.update(id, dto, userId);
  // }
}