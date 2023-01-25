package com.habilisoft.doce.api.reporting.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailReportRequest extends ExportRequest{
    private List<String> recipients;
}
