# build stage
FROM node:12.2.0-alpine AS build

WORKDIR /app

RUN npm install --silent

COPY . .

RUN npm run build

# production stage
FROM nginx:1.16.0-alpine

WORKDIR /app
COPY --from=build /app/build /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
