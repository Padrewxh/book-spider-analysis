# AGENTS.md

本项目是 `Python 爬虫 + Spring Boot + Vue` 的图书商品数据采集与可视化分析系统。

给 Codex 的协作规则：

1. 修改代码后尽量运行对应检查命令，发现环境缺失时要明确说明。
2. Python 代码检查：`python -m py_compile crawler/spider.py crawler/clean_data.py crawler/db.py crawler/config.py`。
3. 后端检查：优先运行 `mvn test`，必要时运行 `mvn -q -DskipTests package`。
4. 前端检查：运行 `npm run build`。
5. 不要删除已有文件，除非用户明确要求或确实必须。
6. 优先保证项目能运行，避免引入复杂但非必要的抽象。
7. 爬虫只面向 Books to Scrape 公开练习站点，不绕过登录、验证码或访问控制。
