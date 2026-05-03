import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";


@Entity()
export class WorkoutType {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @Column()
  name!: string; // Running, Biking, Swimming

  @Column('float')
  caloriesPerMinute!: number;
}