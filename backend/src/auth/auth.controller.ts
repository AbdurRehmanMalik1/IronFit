import {
  Body,
  Controller,
  Get,
  Post,
  Req,
  Res,
  UnauthorizedException,
} from '@nestjs/common';
import type { Request, Response } from 'express';
import { AuthService } from './auth.service';
import { LoginDto } from './dto/login.dto';
import { SignupDto } from './dto/signup.dto';

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('signup')
  async signup(@Body() dto: SignupDto, @Req() req: Request) {
    const user = await this.authService.signup(dto);
    req.session.userId = user.id;
    return { message: 'Signup successful.', user };
  }

  @Post('login')
  async login(@Body() dto: LoginDto, @Req() req: Request) {
    const user = await this.authService.login(dto);
    req.session.userId = user.id;
    return { message: 'Login successful.', user };
  }

  @Get('me')
  async me(@Req() req: Request) {
    if (!req.session.userId) {
      throw new UnauthorizedException('Not authenticated.');
    }

    const user = await this.authService.getUserById(req.session.userId);
    return { user };
  }

  @Post('logout')
  async logout(@Req() req: Request, @Res({ passthrough: true }) res: Response) {
    await new Promise<void>((resolve, reject) => {
      req.session.destroy((err) => {
        if (err) {
          reject(new Error('Failed to destroy session.'));
          return;
        }
        resolve();
      });
    });
    res.clearCookie('workout.sid');

    return { message: 'Logged out.' };
  }
}
