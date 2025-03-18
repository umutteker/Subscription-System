## **1️⃣ Transaction Süreci ve Rollback Mekanizması**

### ** Ödeme Süreci Transaction Akışı**
1️⃣ **Kullanıcı bir abonelik başlatır.**  
   - `subscription-service`, Kafka'ya **`payment_request`** mesajı gönderir.
2️⃣ **`payment-service` ödeme işlemini gerçekleştirir.**  
   - Ödeme başarılı olursa, **`payment_success`** mesajı yayınlanır.
   - Ödeme başarısız olursa, **`payment_failed`** mesajı yayınlanır.
3️⃣ **`subscription-service` ödeme sonucuna göre işlem yapar.**  
   - Başarılı ödemelerde **abonelik süresi uzatılır.**
   - Başarısız ödemelerde **abonelik iptal edilir (rollback).**
4️⃣ **`notification-service` kullanıcılara bilgilendirme mesajı gönderir.**

### ** Rollback Mekanizması (SAGA Compensation)**
Eğer bir **ödeme başarısız olursa**, **`subscription-service` rollback işlemi yapar:**  
- Abonelik **PASSIVE** duruma çekilir.
- Kullanıcıya ödeme başarısızlığı bildirilir.
- Eğer **renew işleminde ödeme başarısız olursa**, abonelik yeniden aktif edilmez.

---

## **2️⃣ Kullanılan Kafka Konuları (Topics)**
- payment_request
- payment_success
- payment_failed
- subscription_cancellation

---

## **3️⃣ Örnek Transaction Senaryosu**

### **Başarılı İşlem Senaryosu**
1️⃣ Kullanıcı abonelik başlatır. (`POST /subscriptions/create` çağrılır.)  
2️⃣ **Kafka'ya `payment_request` mesajı gönderilir.**  
3️⃣ `payment-service`, **%70 ihtimalle başarılı ödeme yapar.**  
4️⃣ **`payment_success` mesajı Kafka'ya gönderilir.**  
5️⃣ `subscription-service`, **aboneliği aktif hale getirir ve süresini uzatır.**
6️⃣ `notification-service`, **kullanıcıya onay bildirimi yollar.**

### **Başarısız İşlem Senaryosu (Rollback)**
1️⃣ Kullanıcı abonelik başlatır. (`POST /subscriptions/create` çağrılır.)  
2️⃣ **Kafka'ya `payment_request` mesajı gönderilir.**  
3️⃣ `payment-service`, **%30 ihtimalle ödeme başarısız olur.**  
4️⃣ **`payment_failed` mesajı Kafka'ya gönderilir.**  
5️⃣ `subscription-service`, **aboneliği iptal eder ve `PASSIVE` duruma çeker.**  
6️⃣ `notification-service`, **kullanıcıya "Ödeme başarısız" bildirimi yollar.**

