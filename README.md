# 基于 Python 爬虫 + Spring Boot + Vue 的图书商品数据采集与可视化分析系统

## 1. 项目简介

本项目面向大学生简历展示，从 0 到 1 实现一个完整的数据采集与可视化系统。数据来源为公开练习网站 [Books to Scrape](https://books.toscrape.com/)，该网站专门用于爬虫练习。本项目仅用于学习，不爬取真实业务网站，不绕过登录、验证码或访问控制。

系统流程：Python 爬虫采集图书列表页和详情页数据，清洗后写入 MySQL；Spring Boot 提供 RESTful API；Vue 3 前端展示图书列表、详情弹窗和 ECharts 可视化看板。

## 2. 项目亮点

- 完整覆盖数据采集、清洗、入库、接口开发、前端展示和可视化分析。
- 爬虫设置 `User-Agent` 和 `time.sleep(0.5)`，符合练习站点的低频访问要求。
- 使用 UPC 唯一索引和 `ON DUPLICATE KEY UPDATE`，支持重复运行且不重复插入。
- 后端采用 controller、service、mapper、entity、vo、dto、config 分层结构。
- 前端使用 Vue Router、Element Plus、Axios 封装和 ECharts 图表组件。

## 3. 技术栈

- 爬虫：Python 3、requests、BeautifulSoup4、pandas、pymysql、tqdm
- 后端：Spring Boot 3、MyBatis-Plus、MySQL、Lombok、RESTful API
- 前端：Vue 3、Vite、Element Plus、Axios、ECharts
- 数据库：MySQL 8+

## 4. 项目结构

```text
book-spider-analysis/
├── crawler/
│   ├── spider.py
│   ├── clean_data.py
│   ├── db.py
│   ├── config.py
│   ├── requirements.txt
│   └── README.md
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/example/bookspider/...
├── frontend/
│   ├── package.json
│   └── src/...
├── sql/
│   └── init.sql
├── README.md
└── AGENTS.md
```

## 5. 数据库设计

数据库名：`book_spider_db`

核心表：`book_info`

字段包括：`id`、`title`、`category`、`price`、`rating`、`stock`、`image_url`、`detail_url`、`upc`、`product_type`、`price_excl_tax`、`price_incl_tax`、`tax`、`availability`、`reviews`、`create_time`、`update_time`。

`upc` 设置唯一索引，用于爬虫重复运行时去重。完整 SQL 在 [sql/init.sql](sql/init.sql)。

## 6. 爬虫运行步骤

```bash
cd book-spider-analysis
mysql -u root -p < sql/init.sql
cd crawler
pip install -r requirements.txt
```

修改 [crawler/config.py](crawler/config.py) 中的 MySQL 用户名和密码，然后运行：

```bash
python spider.py
```

运行结果：

- 数据写入 MySQL 表 `book_info`
- 同时生成 `crawler/books.csv`

## 7. 后端运行步骤

修改 [backend/src/main/resources/application.yml](backend/src/main/resources/application.yml) 中的 MySQL 用户名和密码。

```bash
cd book-spider-analysis/backend
mvn spring-boot:run
```

默认后端地址：

```text
http://localhost:8080
```

## 8. 前端运行步骤

```bash
cd book-spider-analysis/frontend
npm install
npm run dev
```

默认前端地址：

```text
http://localhost:5173
```

如需修改后端 API 地址，可设置环境变量：

```bash
VITE_API_BASE_URL=http://localhost:8080/api
```

## 9. 接口文档

| 接口 | 方法 | 说明 |
| --- | --- | --- |
| `/api/books?page=1&size=10` | GET | 查询图书分页列表 |
| `/api/books/search?keyword=xxx&page=1&size=10` | GET | 按关键词搜索书名、分类或 UPC |
| `/api/books/category/{category}` | GET | 按分类查询 |
| `/api/books/categories` | GET | 查询所有分类 |
| `/api/stat/overview` | GET | 查询统计总览 |
| `/api/stat/category-count` | GET | 分类数量统计 |
| `/api/stat/rating-count` | GET | 评分分布统计 |
| `/api/stat/price-range` | GET | 价格区间统计 |

统一返回格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

## 10. 项目截图位置说明

建议运行前后端后截图并放入 `docs/images/`：

- `dashboard.png`：首页 Dashboard，展示统计卡片和图表。
- `book-list.png`：图书列表页，展示搜索、筛选和分页。
- `book-detail.png`：图书详情弹窗，展示封面和商品详情。
## 11. 常见问题排查

- MySQL 连接失败：检查 MySQL 是否启动，确认 `config.py` 和 `application.yml` 的用户名、密码、端口、数据库名是否正确。
- 后端启动失败：先执行 `mysql -u root -p < sql/init.sql` 创建数据库表，再启动 Spring Boot。
- 前端请求失败：确认后端已启动在 `http://localhost:8080`，或通过 `VITE_API_BASE_URL` 修改 API 地址。
- 页面无数据：先运行爬虫，确认 `book_info` 表中已有数据。
- Maven 依赖下载失败：检查网络或 Maven 镜像配置。
- npm install 失败：检查 Node.js 和 npm 是否安装，必要时配置 npm 镜像源。
