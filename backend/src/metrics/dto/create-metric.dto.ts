import { IsDateString, IsNumber, IsOptional, IsString, IsUUID } from 'class-validator';

export class CreateMetricDto {
  @IsString()
  type!: string; // water, calories, heart_rate

  @IsNumber()
  value!: number;

  @IsOptional()
  @IsDateString()
  recordedAt?: string;
}