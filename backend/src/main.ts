import { ValidationPipe } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import { ConfigService } from '@nestjs/config';
import connectPgSimple from 'connect-pg-simple';
import session from 'express-session';
import { Pool } from 'pg';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule, {
    logger: ['log', 'error', 'warn', 'debug', 'verbose'],
  });

  const configService = app.get(ConfigService);

  app.useGlobalPipes(
    new ValidationPipe({
      whitelist: true,
      transform: true,
      forbidNonWhitelisted: true,
    }),
  );

  app.enableCors({
    origin: true,
    credentials: true,
  });

  // 🔥 REQUEST LOGGER
  app.use((req: any, res: any, next: any) => {
    const start = Date.now();
    console.log(`➡️ ${req.method} ${req.url}`);

    res.on('finish', () => {
      const duration = Date.now() - start;
      console.log(
        `⬅️ ${req.method} ${req.url} ${res.statusCode} - ${duration}ms`,
      );
    });

    next();
  });


  const databaseUrl = configService.get<string>('DATABASE_URL');
  if (!databaseUrl) {
    throw new Error('DATABASE_URL is missing.');
  }

  const pgPool = new Pool({
    connectionString: databaseUrl,
    ssl: { rejectUnauthorized: false },
  });

  const PgStore = connectPgSimple(session);

  app.use(
    session({
      name: 'workout.sid',
      store: new PgStore({
        pool: pgPool,
        tableName: 'user_sessions',
        createTableIfMissing: true,
      }),
      secret:
        configService.get<string>('SESSION_SECRET') ?? 'dev-session-secret',
      resave: false,
      saveUninitialized: false,
      cookie: {
        httpOnly: true,
        secure: configService.get<string>('NODE_ENV') === 'production',
        sameSite: 'lax',
        maxAge: 1000 * 60 * 60 * 24 * 7,
      },
    }),
  );

    // 🔥 SESSION LOGGER
  app.use((req: any, _res: any, next: any) => {
    console.log('🧠 Session ID:', req.sessionID);
    console.log('👤 Session:', req.session);
    next();
  });

  await app.listen(configService.get<number>('PORT') ?? 3000, '0.0.0.0');
}
void bootstrap();
