import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";

@Entity()
export class MetricDefinition {
  @PrimaryGeneratedColumn('uuid')
  id!: string;

  @Column({ unique: true })
  key!: string; // water, calories, heart_rate

  @Column()
  name!: string;

  @Column()
  unit!: string;

  @Column({ nullable: true })
  icon!: string; // icon key (not image file)

  @Column({ nullable: true })
  color!: string;
}