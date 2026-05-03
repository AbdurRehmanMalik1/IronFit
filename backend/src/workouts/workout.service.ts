import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { IsNull, Repository } from 'typeorm';
import { Workout } from 'src/entities/workout.entity';
import { WorkoutType } from 'src/entities/workout.type.entity';

@Injectable()
export class WorkoutsService {
  constructor(
    @InjectRepository(Workout)
    private workoutRepo: Repository<Workout>,

    @InjectRepository(WorkoutType)
    private typeRepo: Repository<WorkoutType>,
  ) { }

  // 1. Get all workout types
  async getWorkoutTypes() {
    return this.typeRepo.find();
  }

  // 2. Start workout
  async startWorkout(userId: string, typeId: string) {

    const active = await this.workoutRepo.findOne({
      where: {
        user: { id: userId },
        endTime: IsNull()
      },

      relations: ['user']
    });

    if (active) {
      throw new Error("User already has an active workout");
    }

    const type = await this.typeRepo.findOne({ where: { id: typeId } });

    if (!type) throw new NotFoundException('Workout type not found');

    const workout = this.workoutRepo.create({
      user: { id: userId } as any,
      type,
      startTime: new Date(),
      isActive: true,
    });

    return this.workoutRepo.save(workout);
  }

  // 3. End workout
  async endWorkout(workoutId: string) {
    const workout = await this.workoutRepo.findOne({
      where: { id: workoutId },
      relations: ['type'],
    });

    if (!workout) throw new NotFoundException('Workout not found');

    const endTime = new Date();

    const durationMs =
      endTime.getTime() - new Date(workout.startTime).getTime();

    const durationMinutes = Math.floor(durationMs / 60000);

    const calories = durationMinutes * workout.type.caloriesPerMinute;

    workout.endTime = endTime;
    workout.durationMinutes = durationMinutes;
    workout.caloriesBurned = calories;
    workout.isActive = false;

    return this.workoutRepo.save(workout);
  }
  async getActiveWorkout(userId: string) {
    return await this.workoutRepo.findOne({
      where: {
        user: { id: userId },
        endTime: IsNull()
      },
      relations: ['user', 'type']
    });
  }
  async getTopWorkouts(userId: string, limit: number) {
    const workouts = await this.workoutRepo
      .createQueryBuilder('workout')
      .leftJoinAndSelect('workout.type', 'type')
      .where('workout.userId = :userId', { userId })
      .andWhere('workout.endTime IS NOT NULL')
      .orderBy('workout.startTime', 'DESC')
      .limit(limit)
      .getMany();
    return workouts;
  }
}