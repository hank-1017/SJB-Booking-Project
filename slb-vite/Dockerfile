# 步驟 1：使用 Node.js 映像來建構靜態文件
FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . ./
RUN npm run build

# 步驟 2：使用 Nginx 提供靜態文件
FROM nginx:stable-alpine

# 複製打包的文件到 Nginx 默認靜態服務目錄
COPY --from=builder /app/dist /usr/share/nginx/html

# 複製自定義的 Nginx 設定文件到 Nginx 配置目錄
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 開放 Nginx 的 80 埠，用於服務應用
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]