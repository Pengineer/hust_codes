初学者项目使用说明

1. SVN资源库中的初学者项目已设置为只读，只能检出，不能提交，以便管理员维护及初学者使用；
2. 在“SVN资源库”窗口中新建一个初学者项目的分支，“右键->分支/标记”，并在目标URL中设置分支项目名称；
3. 在Oracle数据库中创建分支项目相应的数据库用户（即schema）后，将初学者项目的数据库导入到该用户，或者利用文件“/Database/Database.sql”初始化数据结构；
4. 根据上述创建的数据库用户，修改“/WebRoot/WEB-INF/pplicationContext.xml”中的数据库设置（包括：url、username、password等）；
5. 根据上述创建的数据库用户，修改“/src/csdc.bean/”中各“*.hbm.xml”文件的shema设置（等于上述创建的数据库用户名即schema）；
6. 修改完成后，通过MyEclipse发布并运行（初始状态下，系统登录的用户名为：admin，密码为：123456）。