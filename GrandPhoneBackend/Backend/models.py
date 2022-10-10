from django.db import models
from django.contrib.postgres.fields import ArrayField


class User(models.Model):
    user_types = [
        ('D', 'Dependant'),
        ('P', 'Protector')
    ]

    name = models.CharField(max_length=50)
    password = models.CharField(max_length=20)
    telephone = models.CharField(max_length=10)
    email = models.EmailField()
    photo = models.FilePathField()
    priority = models.BooleanField(default=0)
    user_type = models.CharField(max_length=20, choices=user_types)

    def __str__(self):
        return f"Protector(name={self.name})"


class Relation(models.Model):
    protector = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='protector_relation')
    dependant = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='dependant_relation')


class Interface(models.Model):
    protector = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='protector_interface')
    dependant = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='dependant_interface')
    file_path = models.FilePathField()


class Notification(models.Model):
    created = models.DateTimeField(auto_now_add=True)
    days = ArrayField(models.BooleanField(default=0))
    status = models.BooleanField(default=0)
    voice_message = models.FilePathField()
    time = models.DateTimeField()
    message = models.TextField()


class Message(models.Model):
    protector = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='protector_message')
    dependant = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='dependant_message')
    voice_message = models.FilePathField()
    message = models.TextField()


class Activity(models.Model):
    activity_types = [
        ('C', 'Call'),
        ('V', 'Video call'),
        ('M', 'Message'),
        ('N', 'Notification')
    ]
    protector = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='protector_activity')
    dependant = models.ForeignKey(to=User, on_delete=models.CASCADE,
                                  related_name='dependant_activity')
    time = models.DateTimeField()
    activity_type = models.CharField(max_length=20, choices=activity_types)
    telephone = models.CharField(max_length=10)
    name = models.CharField(max_length=50)
    message = models.TextField()
