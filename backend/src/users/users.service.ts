import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { User } from 'src/entities/user.entity';
import { UpdateUserDto } from './dto/update-user.dto';

@Injectable()
export class UsersService {
  constructor(
    @InjectRepository(User)
    private readonly userRepo: Repository<User>,
  ) {}

  // GET ALL USERS (admin/dashboard)
  findAll(): Promise<User[]> {
    return this.userRepo.find({
      order: { createdAt: 'DESC' },
    });
  }

  // GET USER BY ID (profile / internal use)
  async findById(id: string): Promise<User> {
    const user = await this.userRepo.findOneBy({ id });

    if (!user) {
      throw new NotFoundException(`User not found`);
    }

    return user;
  }

  // GET USER BY EMAIL (for auth module lookup only)
  findByEmail(email: string): Promise<User | null> {
    return this.userRepo.findOneBy({ email });
  }

  // UPDATE USER PROFILE (safe fields only)
  async update(id: string, dto: UpdateUserDto): Promise<User> {
    const user = await this.findById(id);

    Object.assign(user, dto);

    return this.userRepo.save(user);
  }

  // DELETE USER (admin only use-case)
  async remove(id: string): Promise<void> {
    const user = await this.findById(id);
    await this.userRepo.remove(user);
  }
}