FROM node:21

WORKDIR /app

COPY . . 

RUN npm install 

RUN npm install @angular/cli -g

CMD ["ng", "serve", "--host", "0.0.0.0"]