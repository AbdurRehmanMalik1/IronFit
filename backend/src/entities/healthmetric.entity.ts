import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class HealthMetric {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User, { onDelete: 'CASCADE' })
  user!: User;

  @Column()
  type!: string; // water, heart_rate, calories, steps

  @Column('float')
  value!: number;

  @Column({ type: 'timestamp' })
  recordedAt!: Date;
}