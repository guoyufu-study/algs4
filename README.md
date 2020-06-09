## 概述

<img src="http://algs4.cs.princeton.edu/cover.png" align="right" hspace="25" width="100" alt="Algorithms 4/e textbook" />

这个 <a href="https://github.com/kevin-wayne/algs4">public repository</a>
包含了 Robert Sedgewick 和 Kevin Wayne 编写的 <a href="http://amzn.to/13VNJi7">Algorithms, 4th Edition</a> 教科书中的算法和客户机的 Java <a href="http://algs4.cs.princeton.edu/code/">源码</a>。
这是官方版本&mdash;它由作者积极维护和更新。
这些程序被组织在 <code>edu.princeton.cs.algs4</code> 包中。
如果你只是需要类文件(并且不需要源码)，那么你可以使用
 <a href="http://algs4.cs.princeton.edu/code/algs4.jar">algs4.jar</a> 替代。

<br/>

## 设计目标

我们最初的目标是介绍 <em>每个程序员都应该知道的50种算法</em>。
我们用<em>程序员</em>这个词来指代那些试图在计算机的帮助下完成某件事情的人，包括科学家、工程师和应用程序开发人员，更不用说理工科和计算机科学专业的大学生了。
为了清晰、可移植性和效率，代码进行了优化。 虽然我们的一些实现与 <tt>java.util</tt> 中的实现一样快(甚至更快)，但我们的主要目标是以一种优雅而简单的方式表达核心算法思想。
虽然我们采用了一些高级 Java 特性(比如泛型和迭代器)，但我们避免了那些干扰解释的东西(比如继承和并发性)。


## 构建管理器

该存储库旨在与 <a href="https://maven.apache.org">Maven</a> 或 <a href="https://gradle.org">Gradle</a> 构建管理器一起使用。它可以从命令行运行，也可以集成到 Eclipse，NetBeans 和 IntelliJ 中。 您也可以通过 <a href="https://bintray.com/algs4/maven/algs4">Bintray</a> 访问它。


## Coursera Algorithms, Part I and II students

Feel free to use this public repository to develop solutions to the programming assignments.
However, please do not store solutions to programming assignments in public repositories.


## Copyright

Copyright &copy; 2000&ndash;2019 by Robert Sedgewick and Kevin Wayne.

## License

此代码在 GPLv3 下发布。

## Contribute to this repository

这个 <a href="http://algs4.cs.princeton.edu/code/wishlist.txt">wishlist.txt</a> 包含了一个要添加到存储库的算法和数据结构的列表。实际上，这个存储库中的一些算法和数据结构是由其他人提供的。如果感兴趣，请遵循与存储库中的代码相同的样式，并在联系我们之前彻底测试您的代码。


## Support for other programming languages

Some of the code in this repository has been translated to other languages:
<ul>
<li><a href="https://github.com/garyaiki/Scala-Algorithms">Scala</a> by Gary Struthers
<li><a href="https://github.com/nguyenqthai/Algs4Net">.NET</a> by Quoc Thai
<li><a href="https://github.com/itu-algorithms/itu.algs4">Python</a> by ITU Copenhagen
<li><a href="https://github.com/shellfly/algs4-py">Python</a> by Li Hao
<li><a href="https://github.com/shellfly/algo">Go</a> by Li Hao
</ul>


## Credits

Thanks to Peter Korgan for Maven and Gradle support.
