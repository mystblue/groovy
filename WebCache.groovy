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

def get(url, title, date, files) {
	def orgFile = files[0].toString()
	def dstFile = files[1].toString()
	download(url, orgFile)
	def encoding = getEncoding(orgFile)
	def buf = new File(orgFile).getText(encoding)
	buf = buf.replaceAll("\r\n", "\n")
	buf = buf.replaceAll("\r", "\n")
	buf = buf.replaceAll("\n", "\r\n")

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
	new File(dstFile).write(buf, "utf-8")
}

def getNumber() {
	for (def i = 1; i < 9; i++) {
		def f1 = new File("archive" + File.separator + "_" + String.format("%04d", i) + ".html")
		def f2 = new File("archive" + File.separator + String.format("%04d", i) + ".html")
		if (!f1.exists() && !f2.exists()) {
			return [f1, f2]
		}
	}
	return null
}

def archive = new File("archive")
if (!archive.exists()) {
	archive.mkdirs()
}

def buf = new File("url.txt").getText("utf-8")
def lines = buf.split("\r\n")
for (def line in lines) {
	def items = line.split(",")
	if (items.length == 3) {
		files = getNumber()
		get(items[0], items[1], items[2], files)
	} else {
		println("書式に誤りがあります。" + line)
	}
}
