import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class DailySummary {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User)
  user!: User;

  @Column()
  date!: string; // "2026-05-02"

  @Column()
  totalSteps!: number;

  @Column('float')
  totalDistance!: number;

  @Column('float')
  calories!: number;

  @Column('float')
  water!: number;

  @Column()
  avgHeartRate!: number;
}