import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";
import { WorkoutType } from "./workout.type.entity";

@Entity()
export class Workout {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User)
  user!: User;

  @ManyToOne(() => WorkoutType)
  type!: WorkoutType;

  @Column({ type: "timestamp", default: () => "CURRENT_TIMESTAMP" })
  startTime!: Date;

  @Column({ type: "timestamp", nullable: true })
  endTime?: Date;

  @Column({ default: 0 })
  durationMinutes!: number;

  @Column({ default: 0 })
  caloriesBurned!: number;

  @Column({ default: false })
  isActive!: boolean;
}