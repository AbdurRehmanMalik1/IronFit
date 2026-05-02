import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication } from '@nestjs/common';
import * as request from 'supertest';
import { AppModule } from './app.module';

describe('AuthController (e2e)', () => {
  let app: INestApplication;
  let agent: request.SuperTest<request.Test>;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();

    // IMPORTANT: enable sessions if you use express-session
    app.use(require('express-session')({
      secret: 'test-secret',
      resave: false,
      saveUninitialized: false,
      cookie: { secure: false },
    }));

    await app.init();

    agent = request.agent(app.getHttpServer());
  });

  afterAll(async () => {
    await app.close();
  });

  const testUser = {
    name: 'Test User',
    email: 'test@example.com',
    password: 'password123',
  };

  it('POST /auth/signup -> should create user and set session', async () => {
    const res = await agent
      .post('/auth/signup')
      .send(testUser)
      .expect(201);

    expect(res.body.message).toBe('Signup successful.');
    expect(res.body.user).toBeDefined();
    expect(res.body.user.email).toBe(testUser.email);
  });

  it('GET /auth/me -> should return logged in user', async () => {
    const res = await agent
      .get('/auth/me')
      .expect(200);

    expect(res.body.user).toBeDefined();
    expect(res.body.user.email).toBe(testUser.email);
  });

  it('POST /auth/logout -> should destroy session', async () => {
    const res = await agent
      .post('/auth/logout')
      .expect(201);

    expect(res.body.message).toBe('Logged out.');
  });

  it('GET /auth/me after logout -> should fail', async () => {
    await agent
      .get('/auth/me')
      .expect(401);
  });

  it('POST /auth/login -> should login again', async () => {
    const res = await agent
      .post('/auth/login')
      .send({
        email: testUser.email,
        password: testUser.password,
      })
      .expect(201);

    expect(res.body.message).toBe('Login successful.');
    expect(res.body.user.email).toBe(testUser.email);
  });
});