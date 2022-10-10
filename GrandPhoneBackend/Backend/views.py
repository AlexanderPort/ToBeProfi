import os

from django.shortcuts import render
from django.http import JsonResponse, HttpResponseServerError, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import default_storage
from django.core.files.base import ContentFile

from random import randint
import json

from GrandPhoneBackend import settings
from .models import User


def parse_data(s: str):
    return dict(map(lambda x: x.split('='), s.split('&')[:-1]))


@csrf_exempt
def post_user(request, *args, **kwargs):
    if request.method == 'POST':
        data = request.POST
        user = User(name=data['userName'],
                    email=data['userEmail'],
                    password=data['userPassword'],
                    telephone=data['userTelephone'],
                    user_type='P' if int(data['userStatus']) else 'D')

        if User.objects.filter(email=user.email).exists():
            return JsonResponse({'status': 'exist'})
        else:
            data = request.FILES['image'].read()
            path = default_storage.save('image.png', ContentFile(data))
            user.photo = os.path.join(settings.MEDIA_ROOT, path)
            user.save()
            return JsonResponse({'status': 'created'})


@csrf_exempt
def getUserByEmailAndName(request, email, password):
    if request.method == 'GET':
        print(email, password)
        return HttpResponse("Got json data")
