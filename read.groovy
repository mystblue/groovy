import java.text.SimpleDateFormat

SETTING_LIST = [['http://alfalfalfa.com/','<div class="main">','<div id="ad2">'],
['http://blog.livedoor.jp/samplems-bakufu/','<div class="article-body-inner">','<TABLE width="100%" cellspacing="1" border="0" cellpadding="0" bgcolor="#dfdfdf">'],
['http://morinogorira.seesaa.net/','<div class="blogbody">','<div id="article-ad"'],
['http://blog.livedoor.jp/nwknews/','<div class="article-title-outer">','<div class="related-articles">'],
['http://blog.livedoor.jp/nicovip2ch/','<h2 class="article-title entry-title">','<div id="ad2">'],
['http://rajic.2chblog.jp/','<div class="article-outer hentry">','<div id="rss-under">'],
['http://blog.livedoor.jp/insidears/','<div id="ad_rs" class="ad_rs_b">','<b><最新記事></b>'],
['http://blog.livedoor.jp/kinisoku/','<div class="article_body">','<h3>コメント</h3>'],
['http://minkch.com/','<div class="article-outer-2" style="text-align:center;">','《オススメ記事》<br />'],
['http://hamusoku.com/','<div class="article-outer hentry">','<div class="article-option" id="comments-list">'],
['http://news.2chblog.jp/','<div class="article-title-outer">','<div class="article-option" id="comments-list">'],
['http://vippers.jp/','<div id="article">','<div id="oneText">'],
['http://nantuka.blog119.fc2.com/','<!--▼ エントリー（記事）▼-->','<div class="bottom_navi">'],
['http://blog.esuteru.com/','<div id="entry">','<div class="clearfix">'],
['http://news4vip.livedoor.biz/','<div class="ently_navi_top">','<div class="article-footer">'],
['http://blog.livedoor.jp/news23vip/','<div class="article-outer hentry">','<div class="article-footer">'],
['http://neetetsu.com/','<div class="article-outer hentry">','<a name="comment-form"></a>'],
['http://nanntokasokuhou.blog.fc2.com/','<!--▼ エントリー（記事）▼-->','<!--▲ エントリー（記事）▲-->'],
['http://blog.livedoor.jp/himasoku123/','<div class="article-body entry-content">','<div class="dashed2">'],
['http://2chcopipe.com/','<div class="article-outer-3">','<div class="article-footer">'],
['http://blog.livedoor.jp/goldennews/','<div class="blogbody">','<div class="formbodytop"></div>'],
['http://yutori2ch.blog67.fc2.com/','<div class="entry">','<div class="form">'],
['http://blog.livedoor.jp/nonvip/','<div class="entry">','<h3>コメント一覧</h3>'],
['http://brow2ing.doorblog.jp/','<div class="article_title">','<div class="article_info">'],
['http://blog.livedoor.jp/negigasuki/','<div class="article-body entry-content">','<div class="article-footer">'],
['http://mudainodqnment.ldblog.jp/','<div class="article-header">','<div class="article-footer">'],
['http://chaos2ch.com/archives/','<div class="article-body entry-content">','<div class="article-footer">'],
['http://news.2chblog.jp/archives/','<div class="article-body entry-content">','<div class="article-option" id="comments-list">'],
['http://mamesoku.com/archives/','<div class="entrybody">','<div class="commentbody">'],
['http://itaishinja.com/archives/','<div class="article-body entry-content">','<div class="sbm">'],
['http://michaelsan.livedoor.biz/archives/','<div class="blogbody">','<div id="ad2"></div>'],
['http://digital-thread.com/archives/','<div class="top-contents">','<h3>コメント一覧</h3>'],
['http://jin115.com/archives/','<div class="article_header">','<div id="comment_list">'],
['http://umashika-news.jp/archives/','<div class="contents-in">','<div class="article-option" id="comment-form">'],
['http://yukkuri.livedoor.biz/archives/','<div class="article-body-more">','<!-- articleBody End -->'],
['http://lifehack2ch.livedoor.biz/archives/','<div class="posted1p">','<div id="commenttop"></div>'],
['http://katuru2ch.blog12.fc2.com/', '<div class="main-entry">', '<ul class="entry-tag">'],
['http://tundaowata.com/archives/', '<h1 class="entry-title">', '<!-- entry_end -->'],
['http://rajic.ldblog.jp/archives/', '<div class="article-date-category-outer">', '<h4>関連記事</h4>'],
['http://vip0nanzo.blog109.fc2.com/', '<h2 class="ently_title">', '<!--▲ エントリー（記事）▲-->'],
['http://blog.livedoor.jp/seiyufan/archives/','<div class="article-body entry-content">','<div id="article-options">'],
['http://rabitsokuhou.2chblog.jp/archives/','<div class="entry">','<div id="article-options">'],
['http://vipsister23.com/archives/','<h2 class="article-title entry-title">','<div id="rewrite-list" class="inline-block">'],
['http://sakusakumirai.blog.fc2.com/','<div class="title_entry">','<div id="comment">'],
['http://blog.livedoor.jp/chihhylove/archives/','<div id="articlebody">','<div id="commenttop">']
]


def scraping(buf, url) {
	def ret = null
	SETTING_LIST.each {
		if (url.startsWith(it[0])) {
			println "found."
			def sindex = buf.indexOf(it[1])
			def eindex = buf.indexOf(it[2])
			if (sindex != -1 && eindex != -1) {
				println "scraping ok."
				ret = buf.substring(sindex, eindex)
			} else {
				println "設定ファイルに誤りがあります。"
			}
		}
	}
	return ret
}

def getMetaURL(buf) {
	def m = buf =~ /<meta name="url" content="([^"]+)"/
	if (m) {
		return m.group(1)
	} else {
		return null
	}
}

def getMetaTitle(buf) {
	def m = buf =~ /<meta name="title" content="([^"]+)"/
	if (m) {
		return m.group(1)
	} else {
		return null
	}
}

def getMetaDate(buf) {
	def m = buf =~ /<meta name="date" content="([^"]+)"/
	if (m) {
		return m.group(1)
	} else {
		return null
	}
}

def normalize(buf) {
	buf = buf.replaceAll(/\r\n/, "")

	buf = buf.replaceAll(/(?im)<img\n[^\/>]*src[ ]*="?([^ "]+)"?[^\/>]*\/?>/, '<img src="\1">')
	buf = buf.replaceAll(/(?im)<a\n[^\/>]*href="?([^ "]+)"?[^\/>]*\/?>/, '<a href="\1">')

	buf = buf.replaceAll(/<!--((?!-->).)*-->/, "")
	buf = buf.replaceAll(/<script((?!<\/script>).)*<\/script>/, "")
	buf = buf.replaceAll(/<noscript((?!<\/noscript>).)*<\/noscript>/, "")

	buf = buf.replaceAll(/(?im)onMouseover="[^"]+"/, "")

	buf = buf.replaceAll(/<h2[^>]*>/, "")
	buf = buf.replaceAll("</h2>", "")

	buf = buf.replaceAll("<strong>", "")
	buf = buf.replaceAll("</strong>", "")

	buf = buf.replaceAll(/(?i)<div[^>]*>/, "")
	buf = buf.replaceAll(/(?i)<\/div>/, "")
	buf = buf.replaceAll(/<span[^>]*>/, "")
	buf = buf.replaceAll("</span>", "")
	buf = buf.replaceAll(/<font[^>]*>/, "")
	buf = buf.replaceAll("</font>", "")
	buf = buf.replaceAll(/<b[^>r][^>]*>/, "")
	buf = buf.replaceAll("<b>", "")
	buf = buf.replaceAll("</b>", "")
	buf = buf.replaceAll("</blockquote>", "")
	buf = buf.replaceAll(/<p[^>a][^>]*>/, "")
	buf = buf.replaceAll("<p>", "")
	buf = buf.replaceAll("</p>", "")
	buf = buf.replaceAll(/<u[^>]*>/, "")
	buf = buf.replaceAll("</u>", "")

	buf = buf.replaceAll(/<dl[^>]*>/, "")
	buf = buf.replaceAll(/<dd[^>]*>/, "\r\n")
	buf = buf.replaceAll("<dt>", "\r\n")
	buf = buf.replaceAll("</dl>", "")
	buf = buf.replaceAll("</dd>", "")
	buf = buf.replaceAll("</dt>", "")

	buf = buf.replaceAll("<a name=\"more\"></a>","")

	buf = buf.replaceAll("&lt;","<")
	buf = buf.replaceAll("&gt;",">")
	buf = buf.replaceAll("&nbsp;"," ")
	buf = buf.replaceAll("&quot;", "\"")

	buf = buf.replaceAll("<br>","\r\n")
	buf = buf.replaceAll("<br/>","\r\n")
	buf = buf.replaceAll("<br />","\r\n")
	buf = buf.replaceAll(/(?m)<br[^>]+>/,"\r\n")

	buf = buf.replaceAll("<imgsrc", "<img src")

	buf = buf.replaceAll(/(?im)<img [^\/>]*src[ ]*="?([^ "]+)"?[^\/>]*\/?>/, '<img src="\1">')
	buf = buf.replaceAll(/(?im)<a [^\/>]*href="?([^ "]+)"?[^\/>]*\/?>/, '<a href="\1">')

	buf = buf.replaceAll("</A>", "</a>")
	buf = buf.replaceAll(/<a href="(http[^\"]+(.jpg|.png|.gif))">[ \n\t]*<img[^>]+>[ \n\t]*<\/a>/, '<img src="\1">')
	buf = buf.replaceAll(/<a href="(http[^\"]+(.jpg|.png|.gif))">[ \n\t]*[^<>]+[ \n\t]*<\/a>/, '<img src="\1">')

	buf = buf.replaceAll(/<a href="[^"]+"><\/a>/, '')

	buf = buf.replaceAll("&#9833;", "?")
	buf = buf.replaceAll("&hellip;", "…")

	buf = buf.replaceAll(/(?m)^\t+/,"")

	buf = buf.replaceAll(/(?m)^[ ]+/,"")

	buf = buf.replaceAll(/(?m)\t+\r\n/,"")

//	buf = buf.replaceAll(/\n/, "\r\n")
//	buf = buf.replaceAll(/\r/, "\r\n")
	buf = buf.replaceAll(/(?ms)(\r\n){2,}/, "\r\n\r\n")

	buf = buf.replaceAll(/^(http[^\"]+(.jpg|.png|.gif))[ ]*/, '<img src="\1">')

	return buf
}

def addMetaInfo(buf, title, url, date) {
	def meta = title + "\r\n" * 2
	meta += "URL：" + url + "\r\n" * 2
	meta += "公開日：" + date + "\r\n"

	def today = new Date()
	def format = new SimpleDateFormat("yyyy-MM-dd")
	meta += "取得日：" + format.format(today) + "\r\n" * 2
	buf = meta + buf
}

if (args.size() == 0) {
	println "記事番号を指定してください。"
	System.exit(0)
} else {
	def num = args[0]
	if (num =~ /[0-9]{4}/) {
		def file = new File("archive" + File.separator + num + ".html")
		if (file.exists()) {
			def buf = file.getText("utf-8")
			def metaURL = getMetaURL(buf)
			def metaTitle = getMetaTitle(buf)
			def metaDate = getMetaDate(buf)
			buf = scraping(buf, metaURL)
			buf = normalize(buf)
			buf = addMetaInfo(buf, metaTitle, metaURL, metaDate)
			new File("archive" + File.separator + num + ".txt").write(buf, "utf-8")
		} else {
			println num + ".html は存在しません。"
		}
	} else {
		println "数字 4 桁で指定してください。"
		System.exit(0)
	}
}
