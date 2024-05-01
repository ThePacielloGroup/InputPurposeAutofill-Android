package com.tpgi.inputpurposeautofill

import android.app.assist.AssistStructure
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveInfo
import android.service.autofill.SaveRequest
import android.util.Log
import android.view.View
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.Toast


class InputPurposeAutofill : AutofillService() {

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        val context: List<FillContext> = request.fillContexts
        val structure: AssistStructure = context[context.size - 1].structure
        traverseStructure(structure)
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {

    }

    private fun traverseStructure(structure: AssistStructure) {
        val windowNodes: List<AssistStructure.WindowNode> =
            structure.run {
                (0 until windowNodeCount).map { getWindowNodeAt(it) }
            }

        windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
            val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
            traverseNode(viewNode)
        }
    }

    private fun traverseNode(viewNode: AssistStructure.ViewNode?) {
        if (viewNode?.autofillHints?.isNotEmpty() == true) {
            if(viewNode.isFocused) {
                var output: String
                if(viewNode.autofillHints?.size!! > 1) {
                    output = "Autofill hints: "
                } else {
                    output = "Autofill hint: "
                }
                output += viewNode.autofillHints.contentToString().substring(1, viewNode.autofillHints.contentToString().length - 1)
                Toast.makeText(this.applicationContext, output, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val children: List<AssistStructure.ViewNode>? =
            viewNode?.run {
                (0 until childCount).map { getChildAt(it) }
            }

        children?.forEach { childNode: AssistStructure.ViewNode ->
            traverseNode(childNode)
        }
    }
}