package provab.herdman.constants;

/**
 * Created by PTBLR-1057 on 5/25/2016.
 */
public class Constants {


 String  query = "SELECT  * from (\n" +
           " SELECT   Details.IdNo as [IDNO], Owner.Code as [Farmer code], Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name [Farmer Name],SMSSetting.Task as [Task],Details.IdNo,SMSSetting.OrderNo,Details.LotNo as lotno\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "                      AND (Details.Status IN (4, 5))\n" +
           "            \n" +
           "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name,Parameter.MilkInterval,[Details].[HeatDate],Details.BreedingStatus, \n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
           "HAVING     SMSSetting.Task='Expected Milking'\n" +
           "AND [Parameter].[MilkInterval]>0\n" +
           "AND Julianday('now')-julianday(details.Calvingdate)  <305\n" +
           "AND Julianday('now')-julianday(details.Calvingdate) % [Parameter].[MilkInterval] =0\n" +
           "Union ALL \n" +
           " --**************END Exp Milking**********************************\n" +
           "--**************                    **********************************      \n" +
           "--**************Start check Heat**********************************\n" +
           "--**************                    **********************************\n" +
           "\n" +
           "\n" +
           "\n" +
           "\n" +
           "--**************Start check Heat**********************************\n" +
           "--**************                    **********************************\n" +
           "\n" +
           "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "\n" +
           "\n" +
           " FROM         Details INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Aborted', 'Open Bred')) \n" +
           "                      GROUP BY Details.BreedingStatus,SMSSetting.OrderNo,Details.HeatDate, Details.IdNo, Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg, \n" +
           "                      Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (21- SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (21 + SMSSetting.ToDays) \n" +
           "                      THEN [Details].[HeatDate] END > 0)\n" +
           "                      AND  SMSSetting.Task='Check for Heat'\n" +
           "                      AND (Details.BreedingStatus IN ('Aborted', 'Open Bred'))\n" +
           "\n" +
           "Union ALL \n" +
           "--**************END Check For Heat**********************************\n" +
           "--**************                  **********************************\n" +
           "\n" +
           "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
           "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat, \n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.calvingdate)  >= ([Parameter].[Heat] - SMSSetting.FromDays) AND \n" +
           "\n" +
           "                     Julianday('now')-julianday(details.calvingdate)  <= ([Parameter].[Heat] + SMSSetting.toDays) THEN [Details].[CalvingDate] END > 0) AND \n" +
           "                      (SMSSetting.Task = 'Check for First Heat')\n" +
           "                      AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
           "--**************Start Non Return**********************************\n" +
           "--**************                    **********************************     \n" +
           " Union ALL                    \n" +
           "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
           "                      AND (Details.BreedingStatus IN ('Open Bred', 'Aborted'))\n" +
           "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.HeatDate, Details.IdNo,\n" +
           "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (42 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (42+ SMSSetting.toDays) OR\n" +
           "                      Julianday('now')-julianday(details.heatdate)  >= (63 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (63 + SMSSetting.toDays) \n" +
           "                      THEN [Details].[HeatDate] END > 0)   \n" +
           "                       AND  SMSSetting.Task='Check for Non Return'  \n" +
           "                       AND (Details.BreedingStatus IN ('Open Bred'))                  \n" +
           "                    \n" +
           "--**************END Non Return**********************************\n" +
           "--**************                    **********************************       \n" +
           "--**************Start PD1 **********************************\n" +
           "--**************                    **********************************   \n" +
           "  Union ALL                 \n" +
           "SELECT   Distinct Details.IdNo || ' '  ||  '+', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL OR\n" +
           "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
           "                      (Details.Disposalflg IS NULL OR\n" +
           "                      Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
           "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
           "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD1, \n" +
           "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[PD1] - SMSSetting.FromDays) AND  Julianday('now')-julianday(details.heatdate)\n" +
           "                      < ([Parameter].[PD1] + SMSSetting.toDays) THEN [Details].HeatDate END > 0) \n" +
           "                       AND  SMSSetting.Task='Check PD1'\n" +
           "                        AND (Details.BreedingStatus IN ('Open Bred'))\n" +
           "Union ALL \n" +
           "--**************END PD1**********************************\n" +
           "--**************                    **********************************       \n" +
           "--**************Start PD2 **********************************\n" +
           "--**************                    **********************************                         \n" +
           "SELECT   Distinct Details.IdNo || ' '  ||  '++', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL OR\n" +
           "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
           "                      (Details.Disposalflg IS NULL OR\n" +
           "                      Details.Disposalflg = 0) AND (Details.PDI IN (0, 1)) AND (Details.PDII = 0)\n" +
           "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
           "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD2, \n" +
           "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "Owner.Phno,SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate)>= ([Parameter].[PD2] - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)\n" +
           "                       <= ([Parameter].[PD2] + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0) \n" +
           "\t\t\t\t\t\tAND  SMSSetting.Task='Check PD2'\n" +
           "\t\t\t\t\t\t AND (Details.BreedingStatus IN ('Open Bred'))\n" +
           " Union ALL \n" +
           " --**************END PD2**********************************\n" +
           "--**************                    **********************************   \n" +
           "--**************Start Exp Dry **********************************\n" +
           "--************** \n" +
           "\n" +
           " SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, \n" +
           " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
           " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "                      AND (Details.Status IN (5))\n" +
           "GROUP BY [Parameter].[Dry_Period],Details.Status,SMSSetting.OrderNo,Details.LotNo,Details.milkDate,  Details.IdNo, Owner.name,[Parameter].[Concalv],[Details].[HeatDate],Details.BreedingStatus, \n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(details.heatdate) <= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) + SMSSetting.toDays THEN MAX(Details.HeatDate) ELSE '' END > 0)      \n" +
           "                       AND  SMSSetting.Task='Dry Off'  \n" +
           "                       AND (Details.Status IN (5))\n" +
           "Union ALL\n" +
           "    \n" +
           "--**************END EXP Dry **********************************\n" +
           "--**************    \n" +
           "--**************Start Exp Calving **********************************\n" +
           "--**************                    **********************************   \n" +
           "\n" +
           "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
           "SMSSetting.Task,\n" +
           "Details.IdNo\n" +
           ",SMSSetting.OrderNo,Details.LotNo\n" +
           "\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
           "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) - SMSSetting.FromDays) AND \n" +
           "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
           "                      \n" +
           "                       AND  SMSSetting.Task='Expected Calving'\n" +
           "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
           " Union ALL  \n" +
           " \n" +
           "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
           "SMSSetting.Task,\n" +
           "Details.IdNo\n" +
           ",SMSSetting.OrderNo,Details.LotNo\n" +
           "\n" +
           "FROM         Details INNER JOIN\n" +
           "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
           "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] ) - SMSSetting.FromDays) AND \n" +
           "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] ) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
           "                      \n" +
           "                       AND  SMSSetting.Task='Expected Calving'\n" +
           "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
           " Union ALL                        \n" +
           " --**************END Exp Calving**********************************\n" +
           "--**************                    **********************************       \n" +
           " --**************Vaccination Shedule**********************************\n" +
           "SELECT   Distinct Details.IdNo || ' '  || VaccineDetails.vaccinename AS [IDNO], Owner.Code, \n" +
           "Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task ,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM        VaccineDetails INNER JOIN\n" +
           "                      Details ON VaccineDetails.Idno = Details.IdNo INNER JOIN\n" +
           "                      Option ON VaccineDetails.vaccinename = Option.Field_name INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,Option.days, Option.Field_name,VaccineDetails.vaccinename,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "HAVING      (CASE WHEN Julianday('now')-julianday(VaccineDetails.Date) >= (Option.Days  * 30) - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(VaccineDetails.Date) \n" +
           "\n" +
           " <= ( Option.Days * 30) + SMSSetting.toDays THEN Julianday('now')-julianday(VaccineDetails.Date) ELSE '+ ' END > 0)\n" +
           " AND  SMSSetting.Task='Due for Vaccination'\n" +
           "\n" +
           "Union ALL\n" +
           "--**************Deworming Shedule**********************************\n" +
           "SELECT   Distinct Details.IdNo || ' '  || Deworming.Deworm AS [IDNO], Owner.Code, Owner.Phno,\n" +
           "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM        Deworming INNER JOIN\n" +
           "                      Details ON Deworming.Idno = Details.IdNo INNER JOIN\n" +
           "                      Option ON Deworming.Deworm = Option.Field_Name INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "GROUP BY SMSSetting.OrderNo,   Details.IdNo, Owner.name,Option.Days, Option.Field_Name,Deworming.Deworm,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "HAVING      (CASE WHEN \n" +
           "Julianday('now')-julianday(Deworming.Date) >= (Option.Days * 30) - SMSSetting.FromDays AND\n" +
           "Julianday('now')-julianday(Deworming.Date)  <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
           "Julianday('now')-julianday(Deworming.Date) ELSE '+ ' END > 0)\n" +
           "              AND  SMSSetting.Task='Due for Deworming'         \n" +
           "Union ALL                      \n" +
           "--**************Medicine followup Shedule**********************************\n" +
           "SELECT   Distinct Details.IdNo || ' '  || MedicineFollow.MedicineName AS [IDNO], Owner.Code, \n" +
           "Owner.Phno,Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                     MedicineFollow ON Details.IdNo =MedicineFollow.IDNO INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) AND \n" +
           "Julianday('now')-julianday(MedicineFollow.Date) >= - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(MedicineFollow.Date)  <= 2\n" +
           "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,MedicineFollow.MedicineName,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "                       HAVING  SMSSetting.Task='Medicine Follow Up'\n" +
           "Union ALL\n" +
           " --**************Treatmnt Shedule**********************************                     \n" +
           "SELECT   Distinct Details.IdNo , Owner.Code, Owner.Phno,\n" +
           " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
           " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                     TreatmentFollow ON Details.IdNo =TreatmentFollow.IDNO INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) AND \n" +
           "Julianday('now')-julianday(TreatmentFollow.Date) >= - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(TreatmentFollow.Date)  <= 2\n" +
           "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "   HAVING  SMSSetting.Task='Treatment Follow Up'\n" +
           "Union ALL\n" +
           "--**************Calf Shedule**********************************\n" +
           "SELECT   Distinct  Details.IdNo || ' '  || CalfShedule.Task, Owner.Code, Owner.Phno,\n" +
           "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
           " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                      CalfShedule ON Details.Breed = CalfShedule.BreedID \n" +
           "                      INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL)\n" +
           "                      AND \n" +
           "Julianday('now')-julianday(Details.DtOfBirth) >= - CalfShedule.Day - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(Details.DtOfBirth) <= CalfShedule.Day + SMSSetting.FromDays\t\t\t\t  \n" +
           "\t\t\t\t\t  \n" +
           "\t\t\t\n" +
           "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,CalfShedule.Task,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "   HAVING  SMSSetting.Task='Calf Shedule'\n" +
           "\n" +
           "Union ALL\n" +
           "--**************Weight gain Shedule**********************************\n" +
           "SELECT   Distinct  Details.IdNo , Owner.Code, Owner.Phno, \n" +
           "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
           "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM         Details INNER JOIN\n" +
           "                     WeightGainDet ON Details.IdNo =WeightGainDet.IdNo\n" +
           "                       INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL)\n" +
           "GROUP BY SMSSetting.OrderNo,  Details.IdNo, Owner.name,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "HAVING      \n" +
           "(Julianday('now')-julianday(WeightGainDet.Date) >= 30 - SMSSetting.FromDays) AND \n" +
           "(Julianday('now')-julianday(WeightGainDet.Date)  <= 30 + SMSSetting.toDays)\n" +
           "   AND  SMSSetting.Task='Due for Body Weight'\n" +
           "\n" +
           "Union ALL\n" +
           "--**************Diseases Testing**********************************Due for Diseases Testing\n" +
           "SELECT   Distinct  Details.IdNo || ' '  || DiseaseTest.Disease, Owner.Code, Owner.Phno, \n" +
           "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
           "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
           "FROM        DiseaseTest INNER JOIN\n" +
           "                      Details ON DiseaseTest.Idno = Details.IdNo INNER JOIN\n" +
           "                      Option ON DiseaseTest.Disease = Option.Field_Name  INNER JOIN\n" +
           "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
           "                      SMSSetting\n" +
           "WHERE     (Details.Disposalflg IS NULL) OR\n" +
           "                      (Details.Disposalflg = 0)\n" +
           "\n" +
           "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name, Option.Days, Option.Field_Name,DiseaseTest.Disease,\n" +
           "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
           "                      SMSSetting.Task,Owner.Code\n" +
           "\n" +
           "HAVING      (CASE WHEN \n" +
           "Julianday('now')-julianday(DiseaseTest.Date)>= (Option.Days * 30) - SMSSetting.FromDays AND \n" +
           "Julianday('now')-julianday(DiseaseTest.Date) <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
           "Julianday('now')-julianday(DiseaseTest.Date) ELSE '+ ' END > 0)\n" +
           "  AND  SMSSetting.Task='Due for Diseases Testing'\n" +
           ") as U \n" +
           "ORDER BY U.[Farmer Name],U.OrderNo, U.IdNo\n" +
           "\n";





































}
