import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class Workout {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User)
  user!: User;

  @Column()
  title!: string; // Running, Biking

  @Column()
  duration!: number; // in minutes

  @Column()
  caloriesBurned!: number;

  @Column({ type: 'timestamp' })
  date!: Date;
}