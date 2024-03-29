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

RUN npm run build

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
# Copy the react build from Stage 1
COPY --from=build /app/build /var/www

# Copy our custom nginx config
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 3000 to the Docker host, so we can access it
# from the outside.
EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]