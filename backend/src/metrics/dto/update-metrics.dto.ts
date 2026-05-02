import { IsDateString, IsNumber, IsOptional } from 'class-validator';

export class UpdateMetricDto {
  @IsOptional()
  @IsNumber()
  value?: number;

  @IsOptional()
  @IsDateString()
  recordedAt?: string;
}