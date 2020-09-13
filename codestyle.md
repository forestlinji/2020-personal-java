# 代码规约

## 一. 命名规约

1. 代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式
2. 类名使用UpperCamelCase风格，必须遵从驼峰形式，但以下情形例外：（领域模型的相关命名）DO / BO / DTO / VO等
3. 除数据类的成员变量外，其余方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式
4. 常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚
5. 包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式

## 二. 格式规约

1. 大括号的使用约定。如果是大括号内为空，则简洁地写成{}即可，不需要换行；如果是非空代码块则：
   - 左大括号前不换行。
   - 左大括号后换行。
   - 右大括号前换行。
   - 右大括号后还有else等代码则不换行；表示终止右大括号后必须换行。

2. 左括号和后一个字符之间不出现空格；同样，右括号和前一个字符之间也不出现空格
3. if/for/while/switch/do等保留字与左右括号之间都必须加空格
4. 任何运算符左右必须加一个空格
5. 缩进采用4个空格，禁止使用tab字符
6. 单行字符数限制不超过  120个，超出需要换行
7. 方法参数在定义和传入时，多个参数逗号后边必须加空格
8. 方法与方法之间插入两个空行

## 三. 注释规约

1. 所有的抽象方法（包括接口中的方法）必须要用Javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。

2. 方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/ */注释，注意与代码对齐

3. 代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改

   

## 四. 并发处理

1. 线程资源必须通过线程池提供，不允许在应用中自行显式创建线程
2. 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险



(本文绝大部分内容来自阿里巴巴Java开发手册)


