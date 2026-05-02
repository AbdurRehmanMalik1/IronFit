import { Controller, Get, Param, Patch, Delete, Body } from '@nestjs/common';
import { UsersService } from './users.service';
import { UpdateUserDto } from './dto/update-user.dto';

@Controller('users')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  // LIST USERS
  @Get()
  findAll() {
    return this.usersService.findAll();
  }

  // GET USER PROFILE
  @Get(':id')
  findById(@Param('id') id: string) {
    return this.usersService.findById(id);
  }

  // UPDATE PROFILE (admin or self-update depending on guards later)
  @Patch(':id')
  update(@Param('id') id: string, @Body() dto: UpdateUserDto) {
    return this.usersService.update(id, dto);
  }

  // DELETE USER (admin-only later via guard)
  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.usersService.remove(id);
  }
}