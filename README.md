Ankara JUG Big Data Demo
======================

Yapılması gerekenler
-----------

**1.** Projeyi Git ([http://git-scm.com/)](http://git-scm.com/)) kullanarak `git clone https://github.com/serkan-ozal/ankarajug-bigdata-demo.git` komutu ile yada proje sayfasında sağ alt tarafta bulunan **Download Zip** ile çekebilirsiniz. 

**2.** `pom.xml` dosyasında bulunan 

~~~
<aws.accessKey>[Your AWS Access Key]</aws.accessKey>
<aws.secretKey>[Your AWS Secret Key]</aws.secretKey>
<aws.accountNo>[Your AWS Account No]</aws.accountNo>
~~~

kısımında kendi hesabınıza ait değerleri girmelisiniz. [https://portal.aws.amazon.com/gp/aws/securityCredentials?)](https://portal.aws.amazon.com/gp/aws/securityCredentials?) adresinde bulunan **Access Credentials** bölümünde bulunan **Create a new Access Key** ile kendinize ait credentials yaratabilirsiniz. Bu credentials dosyasının içinde size ait AWS **Access Key** ve **Secret Key** bulunmaktadır.

**3.** Projeyi derlemek, build etmek ve deploy etmek için Maven ([http://maven.apache.org/](http://maven.apache.org/)) gereklidir. Maven'ın nasıl kullanılacaği ile ilgili bilgiyi [http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) adresinden bulabilirsiniz.

Projeyi build etmek için gerekli komutlar proje dizinindeki `build.sh` (yada Windows ortamında ise `build.bat`) dosyasında, deploy etmek için gerekli komutlar da `deploy.sh` (yada Windows ortamında ise `deploy.bat`) dosyasında bulunmaktadır.

Deploy sonucu size ait olan Map/Reduce job jar dosyası `ankarajug-bigdata-demo-mapreduce-[Your AWS Account No]` dizininde oluşacaktır. Bu dizini deploy yapmadan önce oluşturmanız gerekmektedir. Bu dizini [https://console.aws.amazon.com/s3](https://console.aws.amazon.com/s3) adresinden **S3** üzerinde **Create Bucket** diyerek oluşturabilirsiniz.

**4.** Map/Reduce job'ını nasıl başlatacağınız ile ilgili gerekli bilgiyi [http://docs.aws.amazon.com/ElasticMapReduce/latest/DeveloperGuide/emr-launch-custom-jar-cli.html](http://docs.aws.amazon.com/ElasticMapReduce/latest/DeveloperGuide/emr-launch-custom-jar-cli.html) adresinden bulabilirsiniz.

<a name="Sample_1"></a>
Örnek-1
-----------
`s3n://ankarajug-bigdata-demo-mapreduce/number-frequency/input/` dizininde bulunan tüm dosyalar içindeki sayıları inceleyerek her bir sayının kaç kere geçtiğini bulan ve sonuçları `s3n://ankarajug-bigdata-demo-mapreduce/number-frequency/output/` dizininde `output.txt` isimli bir dosyaya yazan bir Map/Reduce uygulamasını yazınız. 



**Açıklamalar:**

1. Her satırda sadece bir sayı vardır. 
2. Çıktıda her satırda sayı ve o sayının kaç kere geçtiği bilgisi olacaktır. 
3. Çıktıdaki sayı ve sıklık değeri arasında bir tane **boşluk** olacaktır. 

**Örnek Girdi:**

~~~~ 
123
456
789
...
~~~~

**Örnek Çıktı:**

~~~
1 1234
...
456 2345
...
789 3456
...
~~~

<a name="Sample_2"></a>
Örnek-2
-----------
`s3n://ankarajug-bigdata-demo-mapreduce/log-data/input/` dizininde bulunan tüm dosyalar içindeki log kayıtlarını inceleyerek belirtilen başlangıç ve bitiş tarihleri arasında kaç tane log kaydı olduğunu bulan ve sonucu  `s3n://ankarajug-bigdata-demo-mapreduce/log-data/output/` dizininde `output.txt` isimli bir dosyaya yazan bir Map/Reduce uygulamasını yazınız. 

**Açıklamalar:**

1. Her satırda JSON formatında sadece bir log kaydı vardır. 
2. Uygulama başlangış ve bitiş tarihlerini parametre olarak alacaktır ve bu tarihler arasındaki (bu tarihler de dahil) log kayıtlarının sayısı bulunacaktır. 
3. Uygulamaya parametre olarak geçilen tarih formatları `dd-MM-yyyy` formatında yani **2 haneli gün** + **"-"** + **2 haneli ay** + **"-"** + **4 haneli yıl** formatında olacaktır.  
4. Eğer her iki tarih de (başlangıç ve bitiş tarihi) parametre olarak girilmez ise tarih koşulu aranmayacak ve tüm log kayıtları sayılacaktır.
5. Eğer sadece bir tane tarih girilmişse, o tarih başlangıç tarihi olarak kabul edilecek ve bitiş tarihi koşulu aranmayacaktır. Bu durumda başlangıç tarihinden itibaren olan (başlangıç tarihi dahil) tüm log kayıtları sayılacaktır.  
6. Çıktıda tek bir satırda belirtilen tarih aralığından toplam log kaydı sayısı değeri olacaktır.

**Örnek Girdi:**

~~~~ json
{"username":"user_1","ip":"111.111.111","date":"Jan 11, 2001 1:22:33 AM"}
{"username":"user_2","ip":"222.222.222","date":"Sep 22, 2002 2:33:44 AM"}
...
~~~~

**Örnek Çıktı:**

~~~
1500000
~~~
