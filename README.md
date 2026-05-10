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

## 11. 简历写法

项目名称：基于 Python 爬虫 + Spring Boot + Vue 的图书商品数据采集与可视化分析系统

项目描述：基于 Books to Scrape 公开练习站点，实现图书商品数据采集、清洗、MySQL 存储、后端 RESTful API 和前端可视化分析，覆盖从数据获取到数据展示的完整流程。

技术栈：Python、requests、BeautifulSoup4、pandas、pymysql、Spring Boot、MyBatis-Plus、MySQL、Vue 3、Element Plus、Axios、ECharts。

项目职责：负责爬虫采集与清洗逻辑、MySQL 表结构设计、Spring Boot 分层接口开发、Vue 管理端页面与 ECharts 图表展示。

项目亮点：支持分页全量采集和详情页字段补全；通过 UPC 唯一索引实现断点式重复运行去重；后端提供分页、搜索、分类和统计接口；前端实现 Dashboard、列表筛选、分页和详情弹窗，适合项目答辩和简历展示。

## 12. 面试介绍话术

1. 为什么做这个项目：我想做一个能体现全栈能力的数据类项目，所以选择公开练习网站 Books to Scrape，从爬虫、数据库、后端接口到前端可视化完整实现。
2. 项目整体流程：Python 爬虫从首页开始遍历分页，进入每本书详情页补全 UPC、税费、库存等字段，清洗后写入 MySQL；Spring Boot 基于 MyBatis-Plus 提供图书查询和统计接口；Vue 前端调用接口并用 Element Plus 和 ECharts 展示。
3. 爬虫如何实现：使用 requests 请求页面，BeautifulSoup 解析列表页和详情页，通过 `urljoin` 处理相对链接，设置 `User-Agent` 和 0.5 秒请求间隔，异常时打印清晰日志。
4. 数据如何清洗：去除价格中的英镑符号，转换为数值；将 One 到 Five 映射为 1 到 5；去除多余空格；评论数从文本中提取整数。
5. 后端接口如何设计：后端按 controller、service、mapper 分层，统一使用 `Result<T>` 返回，图书列表用 MyBatis-Plus 分页，统计接口用 SQL 聚合完成。
6. 前端如何可视化：前端封装 Axios 请求和 ECharts 图表组件，Dashboard 展示总览指标、分类柱状图、评分饼图和价格区间柱状图，列表页支持搜索、分类筛选、分页和详情弹窗。
7. 项目难点和解决方案：难点主要是多页面数据字段合并和重复运行去重。解决方案是列表页保留基础字段，详情页补全商品字段，最终以 UPC 作为唯一键入库，重复运行时更新已有数据。

## 13. 常见问题排查

- MySQL 连接失败：检查 MySQL 是否启动，确认 `config.py` 和 `application.yml` 的用户名、密码、端口、数据库名是否正确。
- 后端启动失败：先执行 `mysql -u root -p < sql/init.sql` 创建数据库表，再启动 Spring Boot。
- 前端请求失败：确认后端已启动在 `http://localhost:8080`，或通过 `VITE_API_BASE_URL` 修改 API 地址。
- 页面无数据：先运行爬虫，确认 `book_info` 表中已有数据。
- Maven 依赖下载失败：检查网络或 Maven 镜像配置。
- npm install 失败：检查 Node.js 和 npm 是否安装，必要时配置 npm 镜像源。
