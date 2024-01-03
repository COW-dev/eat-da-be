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
                            "latitude": "37.5802219",
                            "longitude": "126.9226047"
                        }
                            """
                )
            ]
        )
    ]
)
annotation class PointApiResponse()
