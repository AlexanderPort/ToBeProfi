import os
import uuid

from django.shortcuts import render
from django.http import JsonResponse, HttpResponseServerError, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import default_storage
from django.core.files.base import ContentFile

from random import randint
import json

from GrandPhoneBackend import settings
from .models import User, Relation


def parse_data(s: str):
    return dict(map(lambda x: x.split('='), s.split('&')[:-1]))


@csrf_exempt
def post_user(request, *args, **kwargs):
    if request.method == 'POST':
        data = request.POST
        print(data)
        user = User(name=data['userName'],
                    email=data['userEmail'],
                    user_type=data['userStatus'],
                    password=data['userPassword'],
                    telephone=data['userTelephone'])
        users = User.objects.filter(email=user.email)
        if users.exists():
            return JsonResponse({'status': 'exist', 'id': users[0].id,
                                 'password': users[0].password})
        else:
            user.id = str(uuid.uuid4())
            data = request.FILES['image'].read()
            path = default_storage.save(f'image{user.id}.png', ContentFile(data))
            user.photo = os.path.join(settings.MEDIA_ROOT, path)
            user.save()
            return JsonResponse({'status': 'created', 'id': user.id})


@csrf_exempt
def post_pd(request, *args, **kwargs):
    if request.method == 'POST':
        data = request.POST
        print(data)
        relation_id = data['relationId']
        protector_id = data['protectorId']
        dependant_name = data['dependantName']
        dependant_email = data['dependantEmail']
        dependant_password = data['dependantPassword']
        protectors = User.objects.filter(id=protector_id)
        if protectors.exists(): protector = protectors[0]
        else: return JsonResponse({'status': 'user does not exist'})
        dependants = User.objects.filter(name=dependant_name,
                                         email=dependant_email,
                                         password=dependant_password)
        if dependants.exists(): dependant = dependants[0]
        else: return JsonResponse({'status': 'user does not exist'})

        relation = Relation(id=relation_id,
                            protector=protector,
                            dependant=dependant)
        relation.save()
        return JsonResponse({'status': 'created', 'id': dependant.id,
                             'telephone': dependant.telephone})


@csrf_exempt
def getUserByEmailAndName(request, email, password):
    if request.method == 'GET':
        print(email, password)
        user = User.objects.filter(email=email, password=password)
        if user.exists():
            user = user[0]
            return JsonResponse({"status": True,
                                 "userName": user.name,
                                 "userEmail": user.email,
                                 "userPassword": user.password,
                                 "userTelephone": user.telephone,
                                 "userStatus": user.user_type})
        else:
            return JsonResponse({"status": False, "message": "Неверный email или пароль"})
