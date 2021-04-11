#### Stage 1: Build the react application
FROM node:13.12.0-alpine as build

# Configure the main working directory inside the docker image. 
# This is the base directory used in any further RUN, COPY, and ENTRYPOINT 
# commands.
WORKDIR /app

# Copy the package.json as well as the package-lock.json and install 
# the dependencies. This is a separate step so the dependencies 
# will be cached unless changes to one of those two files 
# are made.
COPY package.json package-lock.json ./
RUN npm install --no-optional && npm cache clean --force

# Copy the main application
COPY . ./

EXPOSE 3000
CMD ["npm", "start"]