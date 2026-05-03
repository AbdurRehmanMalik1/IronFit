import { Controller, Get, Post, Body, Param, UseGuards } from '@nestjs/common';
import { WorkoutsService } from './workout.service';
import { SessionGuard } from 'src/auth/auth.guard';
import { UserId } from 'src/decorators/UserIdDecorator';


@UseGuards(SessionGuard)
@Controller('workouts')
export class WorkoutsController {
  constructor(private readonly workoutsService: WorkoutsService) { }


  @Get('types')
  getTypes() {
    return this.workoutsService.getWorkoutTypes();
  }


  @Post('start')
  startWorkout(
    @UserId() userId: string,
    @Body('typeId') typeId: string
  ) {
    return this.workoutsService.startWorkout(userId, typeId);
  }

  @Post('end/:id')
  endWorkout(
    @Param('id') workoutId: string
  ) {
    return this.workoutsService.endWorkout(workoutId);
  }
  @Get('active')
  getActiveWorkout(@UserId() userId: string) {
    return this.workoutsService.getActiveWorkout(userId);
  }

  @Get('top/:limit')
  getTopWorkouts(
    @UserId() userId: string,
    @Param('limit') limit: string
  ) {
    return this.workoutsService.getTopWorkouts(userId, Number(limit));
  }
}