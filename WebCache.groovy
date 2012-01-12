import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.GZIPInputStream

def download(src, dst) {
	def url = new URL(src)
	def conn = url.openConnection()
	def is = conn.getInputStream()
	
	def encodings = conn.getHeaderFields().get("Content-Encoding")
	if (encodings != null) {
		for (def item : encodings) {
			if (item.equals("gzip")) {
				is = new GZIPInputStream(is)
			}
		}
	}
	
	def out = new FileOutputStream(new File(dst), false)
	def bos = new BufferedOutputStream(out)
	int b
	while ((b = is.read()) != -1) {
		bos.write(b)
	}
	bos.close()
	is.close()
}

def getEncoding(src) {
	def buf = new File(src).getText("ISO-8859-1")
	def pattern = Pattern.compile("charset[ ]*=[ ]*\"?([0-9a-zA-Z|\\-|_]+)\"?")
	def matcher = pattern.matcher(buf)
	if (matcher) {
		return matcher.group(1)
	} else {
		println "文字コードが不明です。"
		return null
	}
}

//download("http://blog.livedoor.jp/kinisoku/archives/3236121.html", "_0001.html")
def encoding = getEncoding("_0001.html")
def buf = new File("_0001.html").getText(encoding)
buf = buf.replaceAll("\r\n", "\n")
buf = buf.replaceAll("\r", "\n")
buf = buf.replaceAll("\n", "\r\n")

def url = "http://blog.livedoor.jp/kinisoku/archives/3236121.html"
def title = "大学生の俺が45歳の人妻のヒモになった話：キニ速　　気になる速報"
def date = "2012-01-12"

def meta = new StringBuffer("<head>")
meta.append("\r\n")
meta.append("<meta name=\"url\" content=\"")
meta.append(url)
meta.append("\">\r\n")
meta.append("<meta name=\"title\" content=\"")
meta.append(title)
meta.append("\">\r\n")
meta.append("<meta name=\"date\" content=\"")
meta.append(date)
meta.append("\">")
buf = buf.replace("<head>", meta)
new File("0001.html").write(buf, "utf-8")
