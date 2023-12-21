package com.mjucow.eatda.presentation.common.example

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [
        Content(
            mediaType = "application/json",
            examples = [
                ExampleObject(
                    name = "example",
                    value = """
        {
            "message": null,
            "body": {
                "content": [
                    {
                        "id": 1,
                        "displayedName": "띵지대",
                        "address": "서울특별시 서대문구 거북골로 34",
                        "imageAddress": null,
                        "location": null
                    },
                    {
                        "id": 2,
                        "displayedName": "띵지대",
                        "address": "서울특별시 서대문구 거북골로 34",
                        "imageAddress": null,
                        "location": null
                    },
                    {
                        "id": 3,
                        "displayedName": "띵지대",
                        "address": "서울특별시 서대문구 거북골로 34",
                        "imageAddress": null,
                        "location": null
                    },
                    {
                        "id": 4,
                        "displayedName": "띵지대",
                        "address": "서울특별시 서대문구 거북골로 34",
                        "imageAddress": null,
                        "location": null
                    },
                    {
                        "id": 5,
                        "displayedName": "띵지대",
                        "address": "서울특별시 서대문구 거북골로 34",
                        "imageAddress": null,
                        "location": null
                    }
                ],
                "pageable": "INSTANCE",
                "first": true,
                "last": true,
                "size": 20,
                "number": 0,
                "sort": {
                    "empty": true,
                    "unsorted": true,
                    "sorted": false
                },
                "numberOfElements": 20,
                "empty": false
            }
        }
                """
                )
            ]
        )
    ]
)
annotation class StoreDtosApiResponse()
