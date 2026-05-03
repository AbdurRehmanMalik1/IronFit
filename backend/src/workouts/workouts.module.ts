import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { WorkoutsController } from './workout.controller';
import { WorkoutsService } from './workout.service';
import { Workout } from 'src/entities/workout.entity';
import { WorkoutType } from 'src/entities/workout.type.entity';

@Module({
  imports: [TypeOrmModule.forFeature([Workout, WorkoutType])],
  controllers: [WorkoutsController],
  providers: [WorkoutsService],
})
export class WorkoutsModule {}