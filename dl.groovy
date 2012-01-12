import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.util.zip.GZIPInputStream


def download(src, dst) {
	def url = new URL(src)
	def conn = url.openConnection()
	def is = conn.getInputStream()
	
	println conn.getHeaderFields()
	def encodings = conn.getHeaderFields().get("Content-Encoding")
	if (encodings != null) {
		for (def item : encodings) {
			if (item.equals("gzip")) {
				is = new GZIPInputStream(is)
			} else if (item.equals("deflate")) {
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
download("http://yutori2ch.blog67.fc2.com/blog-entry-3331.html", "0001.html")
//download("http://plaza.rakuten.co.jp/nakamuramiiu/", "miu.html")
