import { Entity, PrimaryGeneratedColumn, ManyToOne, Column } from "typeorm";
import { User } from "./user.entity";

@Entity()
export class Goal {
  @PrimaryGeneratedColumn('uuid')
  id!: string;
    
  @ManyToOne(() => User)
  user!: User;

  @Column()
  type!: string; // "steps", "calories"

  @Column()
  target!: number;

  @Column()
  current!: number;

  @Column({ type: 'timestamp' })
  startDate!: Date;

  @Column({ type: 'timestamp' })
  endDate!: Date;
}