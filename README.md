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

**5.** Örnek Hadoop 2 - YARN ile yapıldığı için Map/Reduce job jar'ını AWS üstünde **AMI version 3.0.3** ve **Hadoop version 2.2.0** ile çalıştırmanız gerekmektedir.

Örnek
-----------
`s3n://ankarajug-bigdata-demo-mapreduce/input/` dizininde bulunan tüm dosyalar içindeki sayılar incelenerek her bir sayının kaç kere geçtiğini bulan bir Map/Reduce uygulamasını yazınız. 

**Açıklamalar:**

1. Her satırda sadece bir sayı vardır. 
2. Çıktıda her satırda sayı ve o sayının kaç kere geçtiği bilgisi olacaktır. 
3. Çıktıdaki sayı ve sıklık değeri arasında bir tane **boşluk** olacaktır. 

**Örnek çıktı:**

~~~
1 1234
...
675 2034
...
~~~



