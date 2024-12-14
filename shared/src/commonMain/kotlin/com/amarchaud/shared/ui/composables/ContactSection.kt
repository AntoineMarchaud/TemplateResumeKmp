package com.amarchaud.shared.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amarchaud.shared.KmpLauncher
import com.amarchaud.shared.ui.models.ContactModel
import org.koin.compose.koinInject

@Composable
fun ContactSection(
    contact: ContactModel,
    kmpLauncher: KmpLauncher = koinInject()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ContactItem(
                    icon = Icons.Default.Email,
                    contentDescription = "Email",
                    onClick = {
                        kmpLauncher.openEmail(contact.email)
                    }
                )
                
                ContactItem(
                    icon = Icons.Default.Phone,
                    contentDescription = "Phone",
                    onClick = {
                        kmpLauncher.openPhone(contact.phone)
                    }
                )
            }

            HorizontalDivider()

            ContactItem(
                icon = Icons.Default.Build,
                text = "Portfolio",
                contentDescription = "GitHub",
                onClick = {
                    kmpLauncher.openUrl(contact.githubUrl)
                }
            )

            ContactItem(
                icon = Icons.Default.Person,
                text = "LinkedIn",
                contentDescription = "LinkedIn",
                onClick = {
                    kmpLauncher.openUrl(contact.linkedInUrl)
                }
            )
        }
    }
}

@Composable
private fun ContactItem(
    icon: ImageVector,
    text: String? = null,
    contentDescription: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        text?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/*
@Preview
@Composable
private fun ContactSectionPreview() {
    TemplateResumeTheme {
        ContactSection(contactModelMock)
    }
}*/