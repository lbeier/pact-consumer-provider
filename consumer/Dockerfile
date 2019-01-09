FROM node:11
ENV NODE_ENV test
RUN npm config set strict-ssl false
COPY . /consumer
WORKDIR /consumer
RUN ls
RUN npm install
RUN npm run contract-tests
