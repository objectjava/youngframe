这是一个开源框架呵呵哒
一、设计原则
1.单一职责原则（Single Responsibility Principle - SRP）
一个类只拥有一个职责
2.开放封闭原则（Open Closed Principle - OCP）
当需求有改动，不要去修改，而是通过一个类继承或者组合的方式进行修改，如果不改变架构，那么就去修改吧
3.里氏替换原则（Liskov Substitution Principle - LSP） 
父类能够替换子类，但子类不一定能替换父类。也就是说，在代码中可以将父类全部替换为子类，程序不会报错，也不会在运行时出现任何异常，但反过来却不一定成立
在继承类时，务必重写（Override）父类中所有的方法，尤其需要注意父类的 protected 方法（它们往往是让您重写的），子类尽量不要暴露自己的 public 方法供外界调用。 
4.最少知识原则（Least Knowledge Principle - LKP） 
在做系统设计时，不要让一个类依赖于太多的其他类，需尽量减小依赖关系，否则，您死都不知道自己怎么死的。
5.接口隔离原则（Interface Segregation Principle - ISP） 
当需要对外暴露接口时，需要再三斟酌，如果真的没有必要对外提供的，就删了吧。一旦您提供了，就意味着，您将来要多做一件事情，何苦要给自己找事做呢。
6.依赖倒置原则（Dependence Inversion Principle - DIP）
 并不是说，所有的类都要有一个对应的接口，而是说，如果有接口，那就尽量使用接口来编程吧。
将以上六大原则的英文首字母拼在一起就是 SOLID（稳定的），所以也称之为 SOLID 原则。
只有满足了这六大原则，才能设计出稳定的软件架构！但它们毕竟只是原则，只是四人帮给我们的建议，有些时候我们还是要学会灵活应变，千万不要生搬硬套，否则只会把简单问题复杂化，切记！ 

CGLib 不支持嵌套增强

[core]
	repositoryformatversion = 0
	filemode = false
	logallrefupdates = true
[remote "origin"]
	url = https://github.com/objectjava/youngframe.git
	fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
	remote = origin
	merge = refs/heads/master
