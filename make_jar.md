= 単一ファイルで動作する jar ファイルの作成方法 =

1. groovy-x.x.x.jar を解凍する jar xvf groovy-x.x.x.jar
2. asm-x.x.jar を解凍する jar xvf asm-x.x.jar
3. groovyc *.groovy で groovy ファイルをコンパイルする
4. jar cfm dst.jar manifest.mf *.class groovy META-INF org で圧縮する
