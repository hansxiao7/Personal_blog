# Personal Blog
Spring Boot + Semantic UI build a full-stack web app

# Development Log
- 9/13/2021
  - Finish coding the main web page (index.html)
- 9/14/2021
  - Finish coding blog content pages
  - [canvas-nest.js](https://www.npmjs.com/package/canvas-nest.js/v/2.0.4): particle-effects are added to all pages
- 9/15/2021
  - Admin panel pages are finished;
  - Modules are added to improve the web appearance
    - [animate.css](https://animate.style/): animation styles for div
    - [Editor.md](https://pandao.github.io/editor.md/): Markdown editor
    - [Prism](https://prismjs.com/): display modules for codes
    - [tocbot](https://tscanlin.github.io/tocbot/): generate a table as the content list
    - [typo.css](https://github.com/sofish/typo.css): text style optimization
    - [qrcode.js](https://davidshimjs.github.io/qrcodejs/): generate QR code
    - [jQuery.scrollTo](https://github.com/flesler/jquery.scrollTo): animated scrolling
- 9/16/2021
  - Config Spring Boot
    - Web
    - JPA
    - MySQL driver
    - AOP (for logging files, need to downgrade Spring to V2.4.0)
      - ```
        <dependency>  
            <groupId>org.springframework.boot</groupId>  
            <artifactId>spring-boot-starter-aop</artifactId>  
        </dependency>
        ```
    - Thymeleaf
    - DevTools
  - Config log files
  - Config static with thymeleaf with th:fragment
