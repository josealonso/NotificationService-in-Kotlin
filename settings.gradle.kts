plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "notificationsapp"
include(":UserService", ":OrderService", "NotificationService", ":EmailService")
include("OrderService")
include("UserService")
include("NotificationService")
include("EmailService")
