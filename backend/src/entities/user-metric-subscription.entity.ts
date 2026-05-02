import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class UserMetricSubscription {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User, { onDelete: 'CASCADE' })
  user!: User;

  @Column()
  metricType!: string; 
  // water, heart_rate, calories, steps

  @Column({ default: true })
  isActive!: boolean;
}