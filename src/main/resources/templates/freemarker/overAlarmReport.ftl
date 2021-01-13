<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
>
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2018-05-07T01:38:20Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8010</WindowHeight>
  <WindowWidth>14810</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>110</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s16">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#BFBFBF" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s17">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom" ss:ShrinkToFit="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s19">
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s20">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
   <NumberFormat ss:Format="@"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="统计报表">
  <Table ss:ExpandedColumnCount="6" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14">
   <Column ss:Index="2" ss:Width="169.5"/>
   <Column ss:Width="157.5"/>
   <Column ss:Width="74.5"/>
   <Column ss:Width="62.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="88.5"/>
   <Row>
    <Cell ss:Index="2" ss:MergeAcross="4" ss:MergeDown="1" ss:StyleID="s20"><Data
      ss:Type="String">${envTitle!}</Data></Cell>
   </Row>
   <Row ss:Index="3">
    <Cell ss:Index="2" ss:StyleID="s19"><Data ss:Type="String">统计时间：${staTime!}</Data></Cell>
    <Cell ss:StyleID="s19"/>
    <Cell ss:StyleID="s19"/>
    <Cell ss:StyleID="s19"><Data ss:Type="String">导出时间：</Data></Cell>
    <Cell ss:StyleID="s19"><Data ss:Type="String">${outTime!}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="2" ss:StyleID="s16"><Data ss:Type="String">所属区域</Data></Cell>
    <Cell ss:StyleID="s16"><Data ss:Type="String">设备名称</Data></Cell>
    <Cell ss:StyleID="s16"><Data ss:Type="String">${allTitle}</Data></Cell>
    <Cell ss:StyleID="s16"><Data ss:Type="String">超标数总量</Data></Cell>
    <Cell ss:StyleID="s16"><Data ss:Type="String">超标百分比</Data></Cell>
   </Row>
   	<#if (alarmRateList?size>0) >
		<#list alarmRateList as itemValue>
		   <Row>
			<Cell ss:Index="2" ss:StyleID="s17"><Data ss:Type="String">${itemValue.areaName!}</Data></Cell>
			<Cell ss:StyleID="s17"><Data ss:Type="String">${itemValue.deviceName!}</Data></Cell>
			<Cell ss:StyleID="s17"><Data ss:Type="String">${itemValue.allCount!}</Data></Cell>
			<Cell ss:StyleID="s17"><Data ss:Type="String">${itemValue.alarmCount!}</Data></Cell>
			<Cell ss:StyleID="s17"><Data ss:Type="String">${itemValue.alarmRate!}</Data></Cell>
		   </Row>
	    </#list>
	</#if>
	<#if (alarmRateList?size==0) >
		   <Row ss:AutoFitHeight="0">
			<Cell ss:Index="2" ss:StyleID="s17"/>
			<Cell ss:StyleID="s17"/>
			<Cell ss:StyleID="s17"/>
			<Cell ss:StyleID="s17"/>
			<Cell ss:StyleID="s17"/>
		   </Row>
	</#if>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>-3</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <FreezePanes/>
   <FrozenNoSplit/>
   <SplitHorizontal>4</SplitHorizontal>
   <TopRowBottomPane>4</TopRowBottomPane>
   <ActivePane>2</ActivePane>
   <Panes>
    <Pane>
     <Number>3</Number>
    </Pane>
    <Pane>
     <Number>2</Number>
     <ActiveRow>8</ActiveRow>
     <ActiveCol>5</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
