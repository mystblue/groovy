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

def getOutputFilename() {
  for (i in 1..1000) {
    def file = new File(String.format("tmp\\%04d.html", i))
    if (!file.exists()) {
      return String.format("tmp\\%04d.html", i)
    }
  }
  return null
}

public static String read(String fileName, String encoding) throws Exception {
	File f = new File(fileName);
	InputStream is = new FileInputStream(f);
	Reader reader = new InputStreamReader(is, encoding);
	BufferedReader  br = new BufferedReader(reader);
	
	int c;
	StringBuffer buffer = new StringBuffer();
	while ( (c = br.read()) != -1 ) {
		buffer.append((char)c);
	}
	br.close();
	
	return buffer.toString();
}

public static Set<String> getSet(String filename, String encoding) throws Exception {
	String buf = read(filename, encoding);
	String[] splited = buf.split("\r\n");
	Set<String> set = new HashSet<String>();
	for (String url : splited) {
		set.add(url);
	}
	return set;
}
def write2(String path, String data) {
    File file = new File(path)
    def writer = new PrintWriter(f, "utf-8")
    def lines = data.s
    while ((line = bis.readLine()) != null) {
        writer.println(line)
    }
    writer.close()
}

def write(src, dst, encoding) {
    def fis = new FileInputStream(src)
    def isr = new InputStreamReader(fis, encoding)
    def bis = new BufferedReader(isr)
    def f = new File(dst)
    def writer = new PrintWriter(f, "utf-8")
    String line
    while ((line = bis.readLine()) != null) {
        writer.println(line)
    }
    bis.close()
    writer.close()
}

def checkSetting(settings) {
  for (s in settings) {
    def sp = s.split(",")
    if (sp.length != 3) {
      println "設定ファイルに誤りがあります > " + s
      return false
    }
  }
  return true
}

/*****************************************************************************/
def encode(src, dst) {
  buf = read(src, "ISO-8859-1")
  if (buf == null) {
    println "null"
  }
  def pattern = Pattern.compile("charset[ ]*=[ ]*([0-9a-zA-Z|\\-|_]+)")
  def matcher = pattern.matcher(buf)
  if (matcher) {
      ebuf = new File(src).getText(matcher.group(1))
      write(src, dst, matcher.group(1))
  } else {
      pattern = Pattern.compile("charset[ ]*=[ ]*\"([0-9a-zA-Z|\\-|_]+)\"")
      matcher = pattern.matcher(buf)
      if (matcher) {
          write(src, dst, matcher.group(1))
      } else {
          println "文字コードが不明です。"
      }
  }
}
/*****************************************************************************/
def normalizeText(str) {

	str = str.replaceAll("<\\?xml[^\\?]*\\?>\r\n", "")
	
	str = str.replaceAll("<!DOCTYPE[^>]*>\r\n", "")
	
	str = str.replaceAll("<html[^>]*>", "")
	str = str.replaceAll("</html>", "")

	str = str.replaceAll("<body[^>]*>", "")
	str = str.replaceAll("</body>", "")

	str = str.replaceAll("(?s)<head>.+</head>\r\n?", "")
	
	str = str.replaceAll("(?s)<!--((?!-->).)*-->", "")
	str = str.replaceAll("(?s)<script((?!</script>).)*</script>", "")
	str = str.replaceAll("(?s)<noscript((?!</noscript>).)*</noscript>", "")

	str = str.replaceAll("<div[^>]*>", "")
	str = str.replaceAll("</div>", "")
	str = str.replaceAll("<span[^>]*>", "")
	str = str.replaceAll("</span>", "")
	str = str.replaceAll("<font[^>]*>", "")
	str = str.replaceAll("</font>", "")
	str = str.replaceAll("<b[^>r]*>", "")
	str = str.replaceAll("</b>", "")
	str = str.replaceAll("<p[^>]*>", "")
	str = str.replaceAll("</p>", "")
	
	str = str.replaceAll("<strong[^>]*>", "")
	str = str.replaceAll("</strong>", "")

	str = str.replaceAll("<a name[^>]*></a>", "")


	str = str.replaceAll("&gt;", ">")
	str = str.replaceAll("&hellip;", "…")
	str = str.replaceAll("&cap;", "∩")
	str = str.replaceAll("&omega;", "Ω")
	str = str.replaceAll("&sub;", "⊂")
	str = str.replaceAll("&acute;", "´")
	str = str.replaceAll("&forall;", "∀")
	str = str.replaceAll("&sup;", "⊃")

	str = str.replaceAll("<dl[^>]*>", "")
	str = str.replaceAll("<dd>", "\r\n")
	str = str.replaceAll("<dt>", "\r\n")
	str = str.replaceAll("</dl>", "")
	str = str.replaceAll("</dd>", "")
	str = str.replaceAll("</dt>", "")

	str = str.replaceAll("[ ]*<br />[ ]*", "\r\n")
	str = str.replaceAll("[ ]*<BR>[ ]*", "\r\n")
	str = str.replaceAll("[ ]*<br>[ ]*", "\r\n")
	str = str.replaceAll("[ ]*<br[^>]*>[ ]*", "\r\n")

	str = str.replaceAll("[ ]+\r\n", "\r\n")

	str = str.replaceAll("(?s)\r\n\r\n(\r\n)+", "\r\n\r\n")
}
def normalize(src, dst, url) {
  def buf = new File(src).getText("UTF-8")
  buf = normalizeText(buf)
  buf = buf + "\r\n" + url
  new File(dst).write(buf, "UTF-8")
}
/*****************************************************************************/
def scrapingText(String str, String startTag, String endTag) {
	int index = str.indexOf(startTag)
	if (index != -1) {
		int lastIndex = str.indexOf(endTag)
		if (lastIndex != -1) {
			return str.substring(index, lastIndex)
		} else {
			return str.substring(index)
		}
	}
	return str
}
def scraping(path, url, settings) {
  for (s in settings) {
    sp = s.split(",")
    if (url.contains(sp[0])) {
      println "scraping ok."
      def buf = new File(path).getText("UTF-8")
      buf = scrapingText(buf, sp[1], sp[2])
      new File(path).write(buf, "UTF-8")
      return
    }
  }
  println "No scraping. "
  println url
  return
}
/*****************************************************************************/

// main
//download(args[0], getOutputFilename())
def s = getSet("read.txt", "utf-8")
def settings = getSet("setting.txt", "utf-8")
def b = checkSetting(settings)
if (!b) {
  return
}
for (url in s) {
  def html = getOutputFilename()
  println "dl > " + html
  download(url, html)
  def encoded = html.substring(0, html.length() -4) + "txt"
  println "encode > " + encoded
  encode(html, encoded)
  scraping(encoded, url, settings)
  def normalized = "data" + html.substring(3, html.length() -5) + "_n.txt"
  println "normalize > " + normalized
  normalize(encoded, normalized, url)
}
