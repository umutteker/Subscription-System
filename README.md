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
| **Topic** | **Producer** | **Consumer** | **Açıklama** |
|-----------|-------------|-------------|-------------|
| `payment_request` | `subscription-service` | `payment-service` | Ödeme başlatma talebi |
| `payment_success` | `payment-service` | `subscription-service`, `notification-service` | Ödeme başarılı mesajı |
| `payment_failed` | `payment-service` | `subscription-service`, `notification-service` | Ödeme başarısız mesajı |
| `subscription_cancellation` | `subscription-service` | `payment-service` | Abonelik iptal bildirimi |

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

---

## **4️⃣ Sonuç & Özet**
✅ **Mikroservisler bağımsız çalışır, birbirleriyle Kafka aracılığıyla haberleşir.**  
✅ **Transaction süreci `payment_success` ve `payment_failed` mesajları ile yönetilir.**  
✅ **Başarısız ödeme senaryolarında `subscription-service` rollback mekanizmasını çalıştırır.**  
✅ **Bu model ile klasik monolitik ACID transaction yerine, event-driven ve dağıtık transaction yönetimi uygulanmış olur.**
