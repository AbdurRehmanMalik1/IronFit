import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class Activity {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @ManyToOne(() => User)
  user!: User;

  @Column()
  type!: string; // "walk", "run"

  @Column()
  steps!: number;

  @Column('float')
  distance!: number; // in km

  @Column('float')
  calories!: number;

  @Column({ type: 'timestamp' })
  date!: Date;
}